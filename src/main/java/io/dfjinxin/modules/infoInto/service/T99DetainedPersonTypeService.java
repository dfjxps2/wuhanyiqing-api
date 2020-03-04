package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.T99DetainedPersonTypeEntity;

import java.util.Map;

/**
 * 滞留人员类型代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:13:08
 */
public interface T99DetainedPersonTypeService extends IService<T99DetainedPersonTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

