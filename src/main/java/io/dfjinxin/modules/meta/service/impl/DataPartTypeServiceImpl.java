package io.dfjinxin.modules.meta.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.DataPartTypeDao;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import io.dfjinxin.modules.meta.service.DataPartTypeService;


@Service("dataPartTypeService")
public class DataPartTypeServiceImpl extends ServiceImpl<DataPartTypeDao, DataPartTypeEntity> implements DataPartTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataPartTypeEntity> page = this.page(
                new Query<DataPartTypeEntity>().getPage(params),
                new QueryWrapper<DataPartTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List queryDataPartList(Long tnmtid) {
        return baseMapper.queryDataPartByTnmtid(tnmtid.intValue());
    }

}