package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.T99AreaEntity;

import java.util.List;
import java.util.Map;

/**
 * 行政区域代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:06
 */
public interface T99AreaService extends IService<T99AreaEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<T99AreaEntity> queryList();
}

