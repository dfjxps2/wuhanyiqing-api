package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.T99AppealTypeEntity;

import java.util.Map;

/**
 * 诉求类型代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:35
 */
public interface T99AppealTypeService extends IService<T99AppealTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

