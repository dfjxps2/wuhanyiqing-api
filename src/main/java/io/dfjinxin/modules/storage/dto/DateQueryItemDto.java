package io.dfjinxin.modules.storage.dto;


import lombok.Data;

@Data
public class DateQueryItemDto {

    private Integer dbid;
    private String dbCnNm;
    private String dbPhysNm;
    private Integer partid;
    private Integer partTypeid;
    private Integer dataTblid;
    private String dataTblCnNm;
}
