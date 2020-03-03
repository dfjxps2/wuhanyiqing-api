/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.sys.controller;

import io.dfjinxin.common.annotation.SysLog;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.sys.entity.SysOrgEntity;
import io.dfjinxin.modules.sys.service.ShiroService;
import io.dfjinxin.modules.sys.service.SysOrgService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/org")
public class SysOrgController extends AbstractController {
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private ShiroService shiroService;

	
	/**
	 * 所有机构列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:org:list")
	public List<SysOrgEntity> list(){
		List<SysOrgEntity> orgList = sysOrgService.list();
		for(SysOrgEntity sysOrgEntity : orgList){
			SysOrgEntity parentOrgEntity = sysOrgService.getById(sysOrgEntity.getParentId());
			if(parentOrgEntity != null){
				sysOrgEntity.setParentName(parentOrgEntity.getName());
			}
		}

		return orgList;
	}
	
	/**
	 * 选择机构(添加、修改机构)
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:org:select")
	public R select(){
		//查询列表数据
		List<SysOrgEntity> orgList = sysOrgService.queryNotButtonList();
		
		//添加顶级机构
		SysOrgEntity root = new SysOrgEntity();
		root.setOrgId(0L);
		root.setName("一级机构");
		root.setParentId(-1L);
		root.setOpen(true);
		orgList.add(root);
		
		return R.ok().put("orgList", orgList);
	}
	
	/**
	 * 机构信息
	 */
	@GetMapping("/info/{orgId}")
	@RequiresPermissions("sys:org:info")
	public R info(@PathVariable("orgId") Long orgId){
		SysOrgEntity org = sysOrgService.getById(orgId);
		return R.ok().put("org", org);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存机构")
	@PostMapping("/save")
	@RequiresPermissions("sys:org:save")
	public R save(@RequestBody SysOrgEntity org){
		//数据校验
		verifyForm(org);
		
		sysOrgService.save(org);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改机构")
	@PostMapping("/update")
	@RequiresPermissions("sys:org:update")
	public R update(@RequestBody SysOrgEntity org){
		//数据校验
		verifyForm(org);
				
		sysOrgService.updateById(org);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除机构")
	@PostMapping("/delete/{orgId}")
	@RequiresPermissions("sys:org:delete")
	public R delete(@PathVariable("orgId") long orgId){
		//if(orgId <= 31){
		//	return R.error("系统机构，不能删除");
		//}

		//判断是否有子机构或按钮
		List<SysOrgEntity> orgList = sysOrgService.queryListParentId(orgId);
		if(orgList.size() > 0){
			return R.error("请先删除子机构");
		}

		sysOrgService.delete(orgId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysOrgEntity org){
		if(StringUtils.isBlank(org.getName())){
			throw new RRException("机构名称不能为空");
		}
		
		if(org.getParentId() == null){
			throw new RRException("上级机构不能为空");
		}

	}
}
