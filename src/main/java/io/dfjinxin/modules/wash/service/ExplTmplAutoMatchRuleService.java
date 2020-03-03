package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.ExplTmplAutoMatchRuleEntity;

import java.util.Map;

/**
 * 探查模板自动匹配规则
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
public interface ExplTmplAutoMatchRuleService extends IService<ExplTmplAutoMatchRuleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

