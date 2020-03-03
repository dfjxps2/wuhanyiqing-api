package io.dfjinxin.modules.storage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.storage.dao.DataDstzDao;
import io.dfjinxin.modules.storage.entity.DataDstzEntity;
import io.dfjinxin.modules.storage.service.DataDstzService;


@Service("dataDstzService")
public class DataDstzServiceImpl extends ServiceImpl<DataDstzDao, DataDstzEntity> implements DataDstzService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataDstzEntity> page = this.page(
                new Query<DataDstzEntity>().getPage(params),
                new QueryWrapper<DataDstzEntity>()
        );

        return new PageUtils(page);
    }

}