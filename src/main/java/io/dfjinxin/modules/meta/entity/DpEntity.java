package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据片
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@Data
@TableName("dp")
public class DpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据片ID
	 */
	@TableId
	private String dpid;
	/**
	 * 数据表ID
	 */
	private Integer dataTblid;
	/**
	 * 数据片日期
	 */
	private Date dpDt;
	/**
	 * 数据片路径
	 */
	private String dpPath;
	/**
	 * 记录数
	 */
	private Integer recQty;

}
