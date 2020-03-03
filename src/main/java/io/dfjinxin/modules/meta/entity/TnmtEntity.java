package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 租户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@Data
@TableName("tnmt")
public class TnmtEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 租户ID
	 */
	@TableId
	private Integer tnmtid;
	/**
	 * 租户名称
	 */
	private String tnmtNm;
	/**
	 * 租户中文名称
	 */
	private String tnmtCnNm;

	/**
	 * 租户类型
	 */
	private Integer tnmtType;
}
