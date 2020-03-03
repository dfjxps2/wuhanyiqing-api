package io.dfjinxin.modules.wash.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataFldWashProjDao;
import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;
import io.dfjinxin.modules.wash.service.DataFldWashProjService;


@Service("dataFldWashProjService")
public class DataFldWashProjServiceImpl extends ServiceImpl<DataFldWashProjDao, DataFldWashProjEntity> implements DataFldWashProjService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataFldWashProjEntity> page = this.page(
                new Query<DataFldWashProjEntity>().getPage(params),
                new QueryWrapper<DataFldWashProjEntity>()
        );

        return new PageUtils(page);
    }

}