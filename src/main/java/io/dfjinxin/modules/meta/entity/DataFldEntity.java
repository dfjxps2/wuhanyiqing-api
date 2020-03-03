package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据字段
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
@Data
@TableName("data_fld")
public class DataFldEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段ID
	 */
	@TableId
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

	/**
	 * 数据表英文名称
	 */
	@TableField(exist=false)
	private String dataTblPhysNm;

	/**
	 * 数据表中文名称
	 */
	@TableField(exist=false)
	private String dataTblCnNm;

	/**
	 * 数据库中文名称
	 */
	@TableField(exist=false)
	private String dbCnNm;

}
