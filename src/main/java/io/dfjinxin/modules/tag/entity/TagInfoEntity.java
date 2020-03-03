package io.dfjinxin.modules.tag.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author z.h.c
 * @email ${email}
 * @date 2019-06-14 16:25:48
 */
@Data
@TableName("label")
public class TagInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签ID
     */
    @TableId
    private Integer labelid;
    /**
     * 标签代码
     */
    private String labelCd;

    /**
     * 是否只读 默认值为0
     */
    private Integer ifReadOnly;

    /**
     * 标签名称
     */
    private String labelNm;
    /**
     * 标签类别ID
     */
    private Integer labelCatgid;

    /**
     * 租户ID
     */
    private Integer tnmtid;
    /**
     * 标签描述
     */
    private String labelDesc;

    /**
     * 用于统计字段标签被使用的个数
     */
    @TableField(exist = false)
    private Integer usedTimes;

    @TableField(exist = false)
    private Integer fldid;

}
