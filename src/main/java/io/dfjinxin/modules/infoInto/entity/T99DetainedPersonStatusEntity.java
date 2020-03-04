package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 滞留人员状态代码表
 * 
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:13:36
 */
@Data
@TableName("t99_detained_person_status")
public class T99DetainedPersonStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 滞留人员状态代码
	 */
	@TableId
	private String detainedPersonStatusCd;
	/**
	 * 滞留人员状态描述
	 */
	private String detainedPersonStatusDesc;

}
