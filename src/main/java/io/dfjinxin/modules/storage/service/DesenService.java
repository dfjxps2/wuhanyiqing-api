package io.dfjinxin.modules.storage.service;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.storage.dto.*;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;

import java.util.List;
import java.util.Map;

public interface DesenService {

    PageUtils query(DesenForm desenForm, Long tenantId);

    List<PartDto> queryDataPartType(Integer tenantId);

    String setting(SaveDesen saveDesen);

    SaveDesen savePro(SaveDesen saveDesen);

    String saveDataFldWashProj(SaveDesen saveDesen);

    void czPrdDataProcJob(SaveDesen saveDesen);

    void deleteDesen(Integer[] deleteIds);

    R queryFldByTbId(Integer tbId);

    List<PrdDataProcJobEntity> queryHistory(Integer tbId);

    List<DbEntity> queryDbsRestrict(Map<String , Object> map);
}
