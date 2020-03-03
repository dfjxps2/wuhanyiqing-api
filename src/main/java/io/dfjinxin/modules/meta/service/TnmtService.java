package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.TnmtEntity;

import java.util.Map;

/**
 * 租户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
public interface TnmtService extends IService<TnmtEntity> {

    PageUtils queryPage(Map<String, Object> params);

    TnmtEntity getTnmtByDepart(String departId);
}

