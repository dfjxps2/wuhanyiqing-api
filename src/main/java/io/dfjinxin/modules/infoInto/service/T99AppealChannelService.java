package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.T99AppealChannelEntity;

import java.util.Map;

/**
 * 诉求渠道代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:15:02
 */
public interface T99AppealChannelService extends IService<T99AppealChannelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

