package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataTblWashProjDao;
import io.dfjinxin.modules.wash.entity.DataTblWashProjEntity;
import io.dfjinxin.modules.wash.service.DataTblWashProjService;


@Service("dataTblWashProjService")
public class DataTblWashProjServiceImpl extends ServiceImpl<DataTblWashProjDao, DataTblWashProjEntity> implements DataTblWashProjService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataTblWashProjEntity> page = this.page(
                new Query<DataTblWashProjEntity>().getPage(params),
                new QueryWrapper<DataTblWashProjEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryRuleList(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<Map<String, Object>> page = baseMapper.queryRuleList(new Page<>(no, limit), params);
        return new PageUtils(page);
    }

}