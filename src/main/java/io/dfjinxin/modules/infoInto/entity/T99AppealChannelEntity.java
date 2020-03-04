package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 诉求渠道代码表
 * 
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:15:02
 */
@Data
@TableName("t99_appeal_channel")
public class T99AppealChannelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 诉求渠道代码
	 */
	@TableId
	private String appealChannelCd;
	/**
	 * 诉求渠道描述
	 */
	private String appealChannelDesc;

}
