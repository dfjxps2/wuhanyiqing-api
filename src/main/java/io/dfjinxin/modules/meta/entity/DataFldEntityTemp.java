package io.dfjinxin.modules.meta.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 数据字段临时表，返回字段信息和字段标签时使用
 */

@Data
public class DataFldEntityTemp implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段ID
	 */
	private Integer fldid;
	/**
	 * 数据表ID
	 */
	private Integer dataTblid;
	/**
	 * 字段物理名称
	 */
	private String fldPhysNm;
	/**
	 * 字段中文名称
	 */
	private String fldCnNm;
	/**
	 * 字段数据类型
	 */
	private String fldDataType;
	/**
	 * 字段描述
	 */
	private String fldDesc;
	/**
	 * 字段顺序
	 */
	private Integer fldOrd;
	/**
	 * 是否主键
	 */
	private Integer ifPk;
	/**
	 * 是否可以为空
	 */
	private Integer ifCanNull;
	/**
	 * 创建日期
	 */
	private Date createDt;
	/**
	 * 更新日期
	 */
	private Date updDt;
	/**
	 * 删除日期
	 */
	private Date delDt;

	private Object fldLabels;

}
