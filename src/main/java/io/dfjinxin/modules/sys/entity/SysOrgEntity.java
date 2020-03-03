/**
 * 2019 东方金信
 *
 *
 *
 *
 */

package io.dfjinxin.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 组织机构管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_org")
public class SysOrgEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 组织机构ID
	 */
	@TableId
	private Long orgId;

	/**
	 * 父组织机构ID，一级组织机构为0
	 */
	private Long parentId;
	
	/**
	 * 父组织机构名称
	 */
	@TableField(exist=false)
	private String parentName;

	/**
	 * 组织机构名称
	 */
	private String name;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	@TableField(exist=false)
	private List<?> list;

}
