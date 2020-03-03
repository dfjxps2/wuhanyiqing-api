package io.dfjinxin.modules.storage.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DesenData {
    private int fldid;
    private String fldCnNm;//字段中文名称
    private String fldPhysNm;//字段英文名称
    private String dataTblCnNm;//表中文名称
    private String dataTblPhysNm;//表英文名称
    private int dataDstzAlgorid;//脱敏算法Id
    private Date setupDt;//脱敏设置日期
    private int ddfs;//调度方式
}
