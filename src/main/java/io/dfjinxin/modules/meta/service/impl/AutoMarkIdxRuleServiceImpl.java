package io.dfjinxin.modules.meta.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.AutoMarkIdxRuleDao;
import io.dfjinxin.modules.meta.entity.AutoMarkIdxRuleEntity;
import io.dfjinxin.modules.meta.service.AutoMarkIdxRuleService;


@Service("autoMarkIdxRuleService")
public class AutoMarkIdxRuleServiceImpl extends ServiceImpl<AutoMarkIdxRuleDao, AutoMarkIdxRuleEntity> implements AutoMarkIdxRuleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AutoMarkIdxRuleEntity> page = this.page(
                new Query<AutoMarkIdxRuleEntity>().getPage(params),
                new QueryWrapper<AutoMarkIdxRuleEntity>()
        );

        return new PageUtils(page);
    }

}