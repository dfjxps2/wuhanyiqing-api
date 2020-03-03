package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 已发布数据处理作业
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Data
@TableName("prd_data_proc_job")
public class PrdDataProcJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 作业ID
	 */
	@TableId
	private Integer jobid;
	/**
	 * 作业类型
	 */
	private Integer jobType;
	/**
	 * 数据表ID
	 */
	private Integer dataTblid;
	/**
	 * 作业发布日期
	 */
	private Date jobReleaseDate;
	/**
	 * 作业执行状态
	 */
	private Integer jobExecStatus;
	/**
	 * 作业启动时间
	 */
	private Date jobStartTime;
	/**
	 * 作业结束时间
	 */
	private Date jobFinishTime;

	/**
	 * 作业失效日期
	 */
	private Date jobExpireDate;

	private Integer jobScheduleType;
}
