package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 * 数据检查项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 11:14:34
 */
@Data
@TableName("data_chk_proj")
public class DataChkProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 检查项目ID
	 */
	@TableId
	private Integer chkProjid;
	/**
	 * 检查项目代码
	 */
	private String chkProjCd;
	/**
	 * 检查项目名称
	 */
	private String chkProjNm;
	/**
	 * 检查项目描述
	 */
	private String chkProjDesc;
	/**
	 * 检查项目状态
	 */
	private Integer chkProjStus;
	/**
	 * 
	 */
	private Integer dataWashCmpuid;
	/**
	 * 是否内置规则
	 */
	private Integer chkProjInternal;
	/**
	 * 检查判断表达式
	 */
	private String chkProjEvalExpr;
	/**
	 * 检查项目执行方式，0：Java UDF，1：Hive正则表达式，2：JavaScript
	 */
	private Integer chkProjEvalType;
	/**
	 * 检查项目源代码
	 */
	private String chkProjSource;
	/**
	 * 租户
	 */
	private Integer tnmtid;

}
