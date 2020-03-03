package io.dfjinxin.modules.meta.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.DataSrcDao;
import io.dfjinxin.modules.meta.entity.DataSrcEntity;
import io.dfjinxin.modules.meta.service.DataSrcService;


@Service("dataSrcService")
public class DataSrcServiceImpl extends ServiceImpl<DataSrcDao, DataSrcEntity> implements DataSrcService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataSrcEntity> page = this.page(
                new Query<DataSrcEntity>().getPage(params),
                new QueryWrapper<DataSrcEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listByTnmt(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.listByTnmt(
                new Query<DataSrcEntity>().getPage(params),
                params
        );

        return new PageUtils(page);
    }

}