package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.dfjinxin.common.aspect.log.SysJsonUtils;
import io.dfjinxin.modules.storage.dto.WashCmpuParasDto;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据清洗运算
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@Data
@TableName("data_wash_cmpu")
public class DataWashCmpuEntity implements Serializable {
	private static Logger logger = LoggerFactory.getLogger(DataWashCmpuEntity.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 数据清洗运算ID
	 */
	@TableId
	private Integer dataWashCmpuid;
	/**
	 * 数据清洗运算代码
	 */
	private String dataWashCmpuCd;
	/**
	 * 数据清洗运算名称
	 */
	private String dataWashCmpuNm;
	/**
	 * 数据清洗运算描述
	 */
	private String dataWashCmpuDesc;

	/**
	 * 数据清洗运算类型
	 */
	private Integer dataWashCmpuType;

	/**
	 * 是否内置规则
	 */
	private Integer dataWashCmpuInternal;
	/**
	 * 判断表达式
	 */
	private String dataWashCmpuJudgeExpr;
	/**
	 * 转换表达式
	 */
	private String dataWashCmpuTransExpr;
	/**
	 * 表达式执行方式，0：Java UDF，1：Hive Regexp，2：Java Script
	 */
	private Integer dataWashCmpuEvalType;
	/**
	 * 判断表达式源代码（JavaScript）
	 */
	private String dataWashCmpuJudgeSouce;
	/**
	 * 转换表达式源代码（JavaScript）
	 */
	private String dataWashCmpuTransSource;

	/**
	 * 清洗运算参数规格说明
	 */
	private String dataWashCmpuParaSpec;

	private long tnmtid;

	@TableField(exist = false)
	private List<WashCmpuParasDto> washCmpuParas;

	public void valWashCmpuPara(){
		if(StringUtils.isNotBlank(this.dataWashCmpuParaSpec)){
			try {
				this.washCmpuParas = SysJsonUtils.jsonToWashCmpuParas(this.dataWashCmpuParaSpec);
			}catch (Exception e){
				logger.error("清洗运算参数规格说明转换json异常：", e);
			}
		}
	}
}
