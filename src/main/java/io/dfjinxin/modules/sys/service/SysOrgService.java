/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.modules.sys.entity.SysOrgEntity;

import java.util.List;


/**
 * 组织机构管理
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysOrgService extends IService<SysOrgEntity> {

	/**
	 * 根据父组织机构，查询子组织机构
	 * @param parentId 父组织机构ID
	 * @param orgIdList  用户组织机构ID
	 */
	List<SysOrgEntity> queryListParentId(Long parentId, List<Long> orgIdList);

	/**
	 * 根据父组织机构，查询子组织机构
	 * @param parentId 父组织机构ID
	 */
	List<SysOrgEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的组织机构列表
	 */
	List<SysOrgEntity> queryNotButtonList();
	
	/**
	 * 获取用户组织机构列表
	 */
	List<SysOrgEntity> getUserOrgList(Long userId);

	/**
	 * 删除
	 */
	void delete(Long orgId);
}
