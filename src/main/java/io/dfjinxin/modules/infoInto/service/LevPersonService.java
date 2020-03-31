package io.dfjinxin.modules.infoInto.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.entity.LevPerson;

import java.util.Map;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/31 17:21
 * @Version: 1.0
 */
public interface LevPersonService extends IService<LevPerson> {
    PageUtils queryPage(Map<String, Object> params);
}
