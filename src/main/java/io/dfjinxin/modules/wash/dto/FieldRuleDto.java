package io.dfjinxin.modules.wash.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("data_fld")
public class FieldRuleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段ID
     */
    private Integer fldid;
    /**
     * 数据表ID
     */
    private Integer dataTblid;
    /**
     * 字段物理名称
     */
    private String fldPsyName;
    /**
     * 字段中文名称
     */
    private String fldCName;
    /**
     * 数据源名称
     */
    private String dataSrcNm;
    /**
     * 元数据状态
     */
    private String metaStatus;
    /**
     * 是否主键
     */
    private String fldKey;
    /**
     * 是否可以为空
     */
    private String fldNull;
    /**
     * 创建日期
     */
    private Date createDt;
    /**
     * 更新日期
     */
    private String updDt;
    /**
     * 删除日期
     */
    private String delDt;

    /**
     * 探查模版ID
     */
    private Integer dataExplTmplid;
    /**
     * 探查模版名称
     */
    private String dataExplTmplNm;

    /**
     * 问题占比
     */
    private String explProRate;
    /**
     * 明细问题分类占比
     */
    private String chkProjNm;
    /**
     * 明细问题分类ID
     */
    private String chkProjid;

    List<Integer> values;
    /**
     * 字段清洗项目
     */
    List<DataFldWashProjEntity> washList;
}