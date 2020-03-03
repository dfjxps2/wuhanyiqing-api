package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 记录状态代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-03 15:46:17
 */
@Data
@TableName("t99_keep_status")
public class T99KeepStatusEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 记录状态代码
	 */
	@TableId
	private String keepStatusCd;
	/**
	 * 记录状态描述
	 */
	private String keepStatusDesc;

}
