package io.dfjinxin.modules.storage.dto;

import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;
import io.dfjinxin.modules.storage.entity.UsrDataQueryEntity;
import lombok.Data;

import java.util.List;

@Data
public class UsrDataQueryDto {

    private UsrDataQueryEntity dataQueryEntity;

    private List<UsrDataQueryCondEntity> dataQueryCondEntityList;

    private List<DataFldEntity> dataFldEntityList;

    private DateQueryItemDto dateQueryItemDto;

}
