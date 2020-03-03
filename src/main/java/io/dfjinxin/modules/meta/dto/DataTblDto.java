package io.dfjinxin.modules.meta.dto;

import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.entity.DbEntity;
import lombok.Data;

import java.util.List;

@Data
public class DataTblDto {

    private String fullName;
    private List<DataFldEntity> fldList;
    private DataTblEntity tbl;
    private DbEntity db;
}
