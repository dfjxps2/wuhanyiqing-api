package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据分区类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Data
@TableName("data_part_type")
public class DataPartTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分区类型ID
	 */
	@TableId
	private Integer partTypeid;
	/**
	 * 分区类型描述
	 */
	private String partTypeDesc;

	/**
	 * data_part表主键partid
	 */
	@TableField(exist = false)
	private Integer partid;

}
