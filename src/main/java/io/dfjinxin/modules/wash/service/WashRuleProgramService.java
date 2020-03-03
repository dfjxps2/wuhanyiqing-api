package io.dfjinxin.modules.wash.service;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.dto.ProgramForm;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;

import java.util.List;

public interface WashRuleProgramService {

   PageUtils query(ProgramForm programForm);
}
