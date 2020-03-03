package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据表标引
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Data
@TableName("data_tbl_mark_idx")
public class DataTblMarkIdxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据表ID
	 */
	@TableId
	private Integer dataTblid;
	/**
	 * 标签ID
	 */
	private Integer labelid;
	/**
	 * 标引类型
	 */
	private Integer markIdxType;

}
