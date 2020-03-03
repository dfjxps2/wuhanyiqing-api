package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 字段探查结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Data
@TableName("fld_expl_result")
public class FldExplResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 作业ID
	 */
	@TableId
	private Integer jobid;
	/**
	 * 字段ID
	 */
	private Integer fldid;
	/**
	 * 检查项目ID
	 */
	private Integer chkProjid;
	/**
	 * 问题记录数
	 */
	private Integer isuRecQty;

}
