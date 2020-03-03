package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DpEntity;

import java.util.Map;

/**
 * 数据片
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
public interface DpService extends IService<DpEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryAll(Map<String, Object> params);
}

