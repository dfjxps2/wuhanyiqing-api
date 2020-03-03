package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据分区
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Data
@TableName("data_part")
public class DataPartEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分区ID
	 */
	@TableId
	private Integer partid;
	/**
	 * 分区类型ID
	 */
	private String partTypeid;
	/**
	 * 租户ID
	 */
	private Integer tnmtid;

}
