package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 已发布作业表级执行结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Data
@TableName("prd_data_proc_job_result")
public class PrdDataProcJobResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 作业ID
	 */
	@TableId
	private Integer jobid;
	/**
	 * 作业执行日期
	 */
	private Date jobExecDate;
	/**
	 * 处理记录总数
	 */
	private Integer procRecTotalQty;
	/**
	 * 完全重复记录数
	 */
	private Integer allDuplRecQty;
	/**
	 * 主键重复记录数
	 */
	private Integer pkDuplRecQty;

}
