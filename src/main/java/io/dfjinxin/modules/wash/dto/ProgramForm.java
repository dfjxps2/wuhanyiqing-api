package io.dfjinxin.modules.wash.dto;

import lombok.Data;

@Data
public class ProgramForm {
    private String dataWashCmpuNm;
    private String chkProjNm;
    private String judgeName;
    private String transName;
    private Integer evalType;
    private Long tnmtid;
    private int pageIndex;
    private int pageSize;
}
