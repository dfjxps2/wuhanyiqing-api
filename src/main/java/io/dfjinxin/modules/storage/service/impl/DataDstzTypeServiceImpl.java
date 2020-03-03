package io.dfjinxin.modules.storage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.storage.dao.DataDstzTypeDao;
import io.dfjinxin.modules.storage.entity.DataDstzTypeEntity;
import io.dfjinxin.modules.storage.service.DataDstzTypeService;


@Service("dataDstzTypeService")
public class DataDstzTypeServiceImpl extends ServiceImpl<DataDstzTypeDao, DataDstzTypeEntity> implements DataDstzTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataDstzTypeEntity> page = this.page(
                new Query<DataDstzTypeEntity>().getPage(params),
                new QueryWrapper<DataDstzTypeEntity>()
        );

        return new PageUtils(page);
    }

}