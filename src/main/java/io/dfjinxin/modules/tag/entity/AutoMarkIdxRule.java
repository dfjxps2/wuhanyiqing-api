package io.dfjinxin.modules.tag.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("auto_mark_idx_rule")
public class AutoMarkIdxRule {

    @TableId
    private Integer ruleid;
    private String ruleDesc;
    private Integer labelid;
    private String labelNm;
    private Integer markIdxObjType;
    private Integer objMatchStgy;
    private String ruleExprs;
    private Integer tnmtid;
}
