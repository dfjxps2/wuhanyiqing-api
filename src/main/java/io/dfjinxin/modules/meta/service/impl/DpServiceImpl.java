package io.dfjinxin.modules.meta.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.DpDao;
import io.dfjinxin.modules.meta.entity.DpEntity;
import io.dfjinxin.modules.meta.service.DpService;


@Service("dpService")
public class DpServiceImpl extends ServiceImpl<DpDao, DpEntity> implements DpService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DpEntity> page = this.page(
                new Query<DpEntity>().getPage(params),
                new QueryWrapper<DpEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryAll(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<Map<String, Object>> page = baseMapper.queryAll(new Page<>(no, limit), params);
        return new PageUtils(page);
    }
}