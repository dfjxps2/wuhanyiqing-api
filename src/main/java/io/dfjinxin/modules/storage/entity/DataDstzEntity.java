package io.dfjinxin.modules.storage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据脱敏
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 15:04:18
 */
@Data
@TableName("data_dstz")
public class DataDstzEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段ID
	 */
	@TableId(type = IdType.INPUT)
	private Integer fldid;
	/**
	 * 设置日期
	 */
	private Date setupDt;
	/**
	 * 数据脱敏类型ID
	 */
	private Integer dataDstzTypeid;
	/**
	 * 数据脱敏算法ID
	 */
	private Integer dataDstzAlgorid;
	/**
	 * 数据脱敏算法参数
	 */
	private String dataDstzAlgorPara;

}
