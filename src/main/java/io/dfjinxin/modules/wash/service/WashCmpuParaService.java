package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.WashCmpuParaEntity;

import java.util.Map;

/**
 * 清洗运算参数
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
public interface WashCmpuParaService extends IService<WashCmpuParaEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

