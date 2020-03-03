package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据库
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@Data
@TableName("db")
public class DbEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据库ID
	 */
	@TableId
	private Integer dbid;
	/**
	 * 数据库物理名称
	 */
	private String dbPhysNm;

	/**
	 * 数据库中文名称
	 */
	private String dbCnNm;
	/**
	 * 分区ID
	 */
	private Integer partid;

	/**
	 * 数据库用途id
	 */
	private Integer dbUsageid;

}
