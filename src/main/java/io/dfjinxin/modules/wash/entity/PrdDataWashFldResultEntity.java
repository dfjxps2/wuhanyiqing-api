package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 已发布作业字段清洗结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Data
@TableName("prd_data_wash_fld_result")
public class PrdDataWashFldResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 作业ID
	 */
	@TableId
	private Integer jobid;
	/**
	 * 作业执行日期（即数据日期）
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date jobExecDate;
	/**
	 * 字段ID
	 */
	private Integer fldid;

	@TableField(exist=false)
	private String fldPhysNm;//字段物理名称

	@TableField(exist=false)
	private String fldCnNm;//字段中文名称

	/**
	 * 数据清洗运算ID
	 */
	private Integer dataWashCmpuid;
	/**
	 * 清洗记录数
	 */
	private Integer washRecQty;
	/**
	 * 退回记录数
	 */
	private Integer retnRecQty;
	/**
	 * 作业执行时间
	 */
	private Date jobExecTime;

}
