/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.sys.entity.SysOrgEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 组织机构管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface SysOrgDao extends BaseMapper<SysOrgEntity> {
	
	/**
	 * 根据父组织机构，查询子组织机构
	 * @param parentId 父组织机构ID
	 */
	List<SysOrgEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的组织机构列表
	 */
	List<SysOrgEntity> queryNotButtonList();

}
