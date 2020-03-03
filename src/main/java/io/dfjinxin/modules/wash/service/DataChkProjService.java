package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.dto.ProgramForm;
import io.dfjinxin.modules.wash.entity.DataChkProjEntity;

import java.util.Map;

/**
 * 数据检查项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
public interface DataChkProjService extends IService<DataChkProjEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils query(ProgramForm programForm, Long tnmtid);

    void saveEntity(DataChkProjEntity dataChkProjEntity);
}

