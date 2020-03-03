package io.dfjinxin.modules.tag.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * @Description: 标签类别信息
 * @Author: z.h.c
 * @CreateDate: 2019/6/13 14:39
 * @Version: 1.0
 */

@Data
@TableName("label_catg")
public class TagTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

//	/**
//	 *
//	 */
//	@TableId
//	private Integer tagTypeCode;
//	/**
//	 *
//	 */
//	private Integer parentTagTypeCode;
//	/**
//	 *
//	 */
//	private String tagTypeName;
//	/**
//	 *
//	 */
//	private String tagTypeDesc;

	/**
	 * 标签类别ID
	 */
	@TableId
	private Integer labelCatgid;
	/**
	 * 上级标签类别ID
	 */
	private Integer suprLabelCatgid;
	/**
	 * 标签类别代码
	 */
	private String labelCatgCd;
	/**
	 * 标签类别名称
	 */
	private String labelCatgNm;
	/**
	 * 标签类别描述
	 */
	private String labelCatgDesc;
	/**
	 * 是否只读
	 */
	private Integer ifReadOnly;
	/**
	 * 租户ID
	 */
	private Integer tnmtid;

	/**
	 * 该标签被表使用次数
	 */
	@TableField(exist = false)
	private Integer usedTimes;

}
