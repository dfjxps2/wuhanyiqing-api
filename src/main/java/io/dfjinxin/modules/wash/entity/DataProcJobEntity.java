package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据处理作业
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Data
@TableName("data_proc_job")
public class DataProcJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 作业ID
	 */
	@TableId
	private Integer jobid;
	/**
	 * 作业状态
	 */
	private String jobStus;
	/**
	 * 作业类型
	 */
	private Integer jobType;
	/**
	 * 启动时间
	 */
	private Date startTm;
	/**
	 * 刷新时间
	 */
	private Date rfrshTm;
	/**
	 * 数据表ID
	 */
	private Integer dataTblid;
	/**
	 * 数据起始日期
	 */
	private Date dataStartDt;
	/**
	 * 数据终止日期
	 */
	private Date dataTerminateDt;
	/**
	 * 数据抽样百分比
	 */
	private Integer dataSplPct;
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
