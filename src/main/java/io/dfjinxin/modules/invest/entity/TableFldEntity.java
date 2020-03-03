package io.dfjinxin.modules.invest.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by GaoPh on 2019/6/19.
 */
@Data
@TableName("data_fld")
public class TableFldEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字段序号
     */
    @TableId
    private String fldId;
    /**
     * 表ID
     */
    private String dataTblid;
    /**
     * 字段中文名
     */
    private String fldCName;

    /**
     * 字段英文名称
     */
    private String fldPsyName;

    /**
     * 数据源
     */
    private String dataSrcNm;

    /**
     * 是否主键
     */
    private String fldKey;

    /**
     * 可为空
     */
    private String fldNull;

    /**
     * 元数据状态
     */
    private String metaStatus;

    /**
     * 更新日期
     */
    private Date uptDt;

    /**
     * 模板id
     */
    private int dataExplTmplid;

    /**
     * 模板名称
     */
    private String dataExplTmplNm;

    /**
     * 探查问题记录比
     * @return
     *
     */
    private String explProRate;

    /**
     *探查问题分类
     * @return
     */
    private String chkProjNm;

    /**
     * 数据清洗运算ID
     * @return
     */
    private String dataWashCmpuid;

    /**
     * 数据清洗运算名称
     * @return
     */
    private String dataWashCmpuNm;

    /**
     * 字段数据类型
     */
    private String fldDataType;
}
