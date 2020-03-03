package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.T99KeepStatusEntity;

import java.util.Map;

/**
 * 记录状态代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-03 15:46:17
 */
public interface T99KeepStatusService extends IService<T99KeepStatusEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

