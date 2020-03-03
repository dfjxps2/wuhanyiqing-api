package io.dfjinxin.modules.wash.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.ExplTmplAutoMatchRuleDao;
import io.dfjinxin.modules.wash.entity.ExplTmplAutoMatchRuleEntity;
import io.dfjinxin.modules.wash.service.ExplTmplAutoMatchRuleService;


@Service("explTmplAutoMatchRuleService")
public class ExplTmplAutoMatchRuleServiceImpl extends ServiceImpl<ExplTmplAutoMatchRuleDao, ExplTmplAutoMatchRuleEntity> implements ExplTmplAutoMatchRuleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<ExplTmplAutoMatchRuleEntity> where = new QueryWrapper<>();
        String key = params.containsKey("key") ? params.get("key").toString() : "";
        String tenantId = params.containsKey("tenantId") ? params.get("tenantId").toString() : "0";
        where.and(w -> w.eq("tnmtid",tenantId));
        if(key.length() > 0){
            where.and(w -> w.like("Rule_Desc", key));
        }

        IPage<ExplTmplAutoMatchRuleEntity> page = this.page(
                new Query<ExplTmplAutoMatchRuleEntity>().getPage(params),
                where
        );

        return new PageUtils(page);
    }

}