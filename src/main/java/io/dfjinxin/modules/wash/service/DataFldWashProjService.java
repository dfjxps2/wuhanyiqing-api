package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;

import java.util.Map;

/**
 * 数据字段清洗项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
public interface DataFldWashProjService extends IService<DataFldWashProjEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

