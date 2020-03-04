package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 行政区域代码表
 * 
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:06
 */
@Data
@TableName("t99_area")
public class T99AreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 行政区域代码
	 */
	@TableId
	private String areaCd;
	/**
	 * 上级行政区域代码
	 */
	private String upstrAreaCd;
	/**
	 * 区县描述
	 */
	private String areaDesc;
	/**
	 * 行政区域层级
	 */
	private String areaLevel;

}
