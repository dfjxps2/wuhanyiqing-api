package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 自动标引规则
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
@Data
@TableName("auto_mark_idx_rule")
public class AutoMarkIdxRuleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 规则ID
	 */
	@TableId
	private Integer ruleid;
	/**
	 * 规则描述
	 */
	private String ruleDesc;
	/**
	 * 标签ID
	 */
	private Integer labelid;
	/**
	 * 标引对象类型
	 */
	private Integer markIdxObjType;
	/**
	 * 对象匹配策略
	 */
	private Integer objMatchStgy;
	/**
	 * 规则表达式
	 */
	private String ruleExprs;
	/**
	 * 租户ID
	 */
	private Integer tnmtid;

}
