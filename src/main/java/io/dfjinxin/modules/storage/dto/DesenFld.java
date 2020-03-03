package io.dfjinxin.modules.storage.dto;

import lombok.Data;

import java.util.List;

@Data
public class DesenFld {
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
    private String fldPhysNm;
    /**
     * 字段中文名称
     */
    private String fldCnNm;

    /**
     * 数据清洗运算ID
     */
    private int dataWashCmpuid;

    private Integer paraid;

    private List<WashCmpuParasDto> washCmpuParas;
}
