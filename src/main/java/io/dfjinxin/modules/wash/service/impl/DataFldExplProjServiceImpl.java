package io.dfjinxin.modules.wash.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataFldExplProjDao;
import io.dfjinxin.modules.wash.entity.DataFldExplProjEntity;
import io.dfjinxin.modules.wash.service.DataFldExplProjService;


@Service("dataFldExplProjService")
public class DataFldExplProjServiceImpl extends ServiceImpl<DataFldExplProjDao, DataFldExplProjEntity> implements DataFldExplProjService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataFldExplProjEntity> page = this.page(
                new Query<DataFldExplProjEntity>().getPage(params),
                new QueryWrapper<DataFldExplProjEntity>()
        );

        return new PageUtils(page);
    }

}