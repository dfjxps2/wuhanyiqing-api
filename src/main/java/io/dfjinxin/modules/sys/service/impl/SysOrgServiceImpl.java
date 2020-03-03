/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.sys.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.Constant;
import io.dfjinxin.common.utils.MapUtils;
import io.dfjinxin.modules.sys.dao.SysOrgDao;
import io.dfjinxin.modules.sys.entity.SysOrgEntity;
import io.dfjinxin.modules.sys.service.SysOrgService;
import io.dfjinxin.modules.sys.service.SysRoleMenuService;
import io.dfjinxin.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysOrgService")
public class SysOrgServiceImpl extends ServiceImpl<SysOrgDao, SysOrgEntity> implements SysOrgService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Override
	public List<SysOrgEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysOrgEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<SysOrgEntity> userMenuList = new ArrayList<>();
		for(SysOrgEntity menu : menuList){
			if(menuIdList.contains(menu.getOrgId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysOrgEntity> queryListParentId(Long parentId) {
		return baseMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysOrgEntity> queryNotButtonList() {
		return baseMapper.queryNotButtonList();
	}

	@Override
	public List<SysOrgEntity> getUserOrgList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}

		//用户组织机构列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long menuId){
		//删除组织机构
		this.removeById(menuId);
		//删除组织机构与角色关联
		sysRoleMenuService.removeByMap(new MapUtils().put("menu_id", menuId));
	}

	/**
	 * 获取所有组织机构列表
	 */
	private List<SysOrgEntity> getAllMenuList(List<Long> menuIdList){
		//查询根组织机构列表
		List<SysOrgEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子组织机构
		getMenuTreeList(menuList, menuIdList);

		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysOrgEntity> getMenuTreeList(List<SysOrgEntity> menuList, List<Long> menuIdList){
		List<SysOrgEntity> subMenuList = new ArrayList<SysOrgEntity>();

		for(SysOrgEntity entity : menuList){
			//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getOrgId(), menuIdList), menuIdList));
			subMenuList.add(entity);
		}

		return subMenuList;
	}
}
