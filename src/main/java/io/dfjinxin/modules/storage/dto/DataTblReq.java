package io.dfjinxin.modules.storage.dto;

import lombok.Data;

@Data
public class DataTblReq {

    private Integer partid;

    private Integer dbid;

    private String tblName;

    private Integer curPage;

    private Integer limit;

}
