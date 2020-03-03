package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.AutoMarkIdxRuleEntity;

import java.util.Map;

/**
 * 自动标引规则
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
public interface AutoMarkIdxRuleService extends IService<AutoMarkIdxRuleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

