package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 诉求类型代码表
 * 
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:35
 */
@Data
@TableName("t99_appeal_type")
public class T99AppealTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 诉求类型代码
	 */
	@TableId
	private String appealTypeCd;
	/**
	 * 诉求类型描述
	 */
	private String appealTypeDesc;

}
