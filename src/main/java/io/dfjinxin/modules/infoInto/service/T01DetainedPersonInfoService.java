package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.T01DetainedPersonInfoEntity;

import java.util.Map;

/**
 * 滞汉外地人明细
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-03 15:21:07
 */
public interface T01DetainedPersonInfoService extends IService<T01DetainedPersonInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    T01DetainedPersonInfoEntity queryById(String id);
}

