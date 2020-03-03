package io.dfjinxin.modules.storage.dto;

import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;
import lombok.Data;

import java.util.List;

@Data
public class DataQueryReq {

    private List<UsrDataQueryCondEntity> dataQueryCondEntityList;

    private DateQueryItemDto dateQueryItemDto;

    private Integer curPage;

    private Integer start;

    private Integer limit;

    private String dataType;

    private Integer dpid;

    private Integer total;
}
