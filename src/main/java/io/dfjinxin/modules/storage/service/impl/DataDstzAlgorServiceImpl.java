package io.dfjinxin.modules.storage.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.storage.dao.DataDstzAlgorDao;
import io.dfjinxin.modules.storage.entity.DataDstzAlgorEntity;
import io.dfjinxin.modules.storage.service.DataDstzAlgorService;


@Service("dataDstzAlgorService")
public class DataDstzAlgorServiceImpl extends ServiceImpl<DataDstzAlgorDao, DataDstzAlgorEntity> implements DataDstzAlgorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataDstzAlgorEntity> page = this.page(
                new Query<DataDstzAlgorEntity>().getPage(params),
                new QueryWrapper<DataDstzAlgorEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<DataDstzAlgorEntity> queryDataDstzAlgorAll(){
        QueryWrapper<DataDstzAlgorEntity> q = new QueryWrapper<DataDstzAlgorEntity>();
        return this.baseMapper.selectList(q);
    }
}