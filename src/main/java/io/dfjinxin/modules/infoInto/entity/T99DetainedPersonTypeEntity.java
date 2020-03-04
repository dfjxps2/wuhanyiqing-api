package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 滞留人员类型代码表
 * 
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:13:08
 */
@Data
@TableName("t99_detained_person_type")
public class T99DetainedPersonTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 滞留人员类型代码
	 */
	@TableId
	private String detainedPersonTypeCd;
	/**
	 * 滞留人员类型描述
	 */
	private String detainedPersonTypeDesc;

}
