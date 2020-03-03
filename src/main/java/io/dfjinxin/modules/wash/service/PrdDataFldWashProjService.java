package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.PrdDataFldWashProjEntity;

import java.util.Map;

/**
 * 已发布数据字段清洗项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-27 14:38:06
 */
public interface PrdDataFldWashProjService extends IService<PrdDataFldWashProjEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

