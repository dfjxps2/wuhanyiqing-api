package io.dfjinxin.modules.tag.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
@TableName("auto_mark_idx_rule")
public class TagRuleEntity {

    /**
     * 规则ID
     */
    @TableId
    private Integer ruleid;

    /**
     * 规则描述
     */
    private String ruleDesc;

    /**
     * 标签ID
     */
    private Integer labelid;

    /**
     * 标引对象类型
     */
    private Integer markIdxObjType;

    /**
     * 对象匹配策略
     */
    private Integer objMatchStgy;

    /**
     * 规则表达式
     */
    private String ruleExprs;

    /**
     * 租户ID
     */
    private Integer tnmtid;




}
