package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 滞汉外地人明细
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-03 15:21:07
 */
@Data
@TableName("t01_detained_person_info")
public class T01DetainedPersonInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 序号
	 */
	@TableId
	private Long id;
	/**
	 * 行政区划代码
	 */
	private String areaCd;
	/**
	 * 姓名
	 */
	private String detainedName;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 身份证号
	 */
	private String cardNumber;
	/**
	 * 滞留人员类型代码
	 */
	private String detainedPersonTypeCd;
	/**
	 * 当地居住地址
	 */
	private String address;
	/**
	 * 目的城市
	 */
	private String destCity;
	/**
	 * 滞留人员状态代码
	 */
	private String detainedPersonStatusCd;
	/**
	 * 诉求渠道代码
	 */
	private String appealChannelCd;
	/**
	 * 安置方式
	 */
	private String resetMode;
	/**
	 * 诉求类型代码
	 */
	private String appealTypeCd;
	/**
	 * 填报用户编号
	 */
	private String submitUserId;
	/**
	 * 审核用户编号
	 */
	private String reviewUserId;
	/**
	 * 填报日期
	 */
	private Date submitDate;
	/**
	 * 审核日期
	 */
	private Date reviewDate;
	/**
	 * 提交日期
	 */
	private Date commitDate;
	/**
	 * 记录状态代码
	 */
	private String keepStatusCd;
	/**
	 * 滞留详情
	 */
	private String detainedInfo;
	/**
	 * 备注
	 */
	private String bz;

	/**
	 * 滞留人员类型描述
	 */
	@TableField(exist = false)
	private String detainedPersonTypeDesc;

	/**
	 * 滞留人员状态描述
	 */
	@TableField(exist = false)
	private String detainedPersonStatusDesc;

	/**
	 *诉求类型描述
	 */
	@TableField(exist = false)
	private String appealTypeDesc;

	/**
	 *诉求渠道描述
	 */
	@TableField(exist = false)
	private String appealChannelDesc;

	/**
	 *记录状态描述
	 */
	@TableField(exist = false)
	private String keepstatusDesc;

}
