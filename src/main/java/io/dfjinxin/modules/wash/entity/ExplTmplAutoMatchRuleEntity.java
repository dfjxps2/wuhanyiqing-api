package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 探查模板自动匹配规则
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Data
@TableName("expl_tmpl_auto_match_rule")
public class ExplTmplAutoMatchRuleEntity implements Serializable {
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
	 * 对象匹配策略
	 */
	private Integer objMatchStgy;
	/**
	 * 规则表达式
	 */
	private String ruleExprs;
	/**
	 * 数据探查模板ID
	 */
	private Integer dataExplTmplid;
	/**
	 * 租户ID
	 */
	private Integer tnmtid;

}
