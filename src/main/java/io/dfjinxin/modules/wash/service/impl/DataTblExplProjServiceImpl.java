package io.dfjinxin.modules.wash.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataTblExplProjDao;
import io.dfjinxin.modules.wash.entity.DataTblExplProjEntity;
import io.dfjinxin.modules.wash.service.DataTblExplProjService;


@Service("dataTblExplProjService")
public class DataTblExplProjServiceImpl extends ServiceImpl<DataTblExplProjDao, DataTblExplProjEntity> implements DataTblExplProjService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataTblExplProjEntity> page = this.page(
                new Query<DataTblExplProjEntity>().getPage(params),
                new QueryWrapper<DataTblExplProjEntity>()
        );

        return new PageUtils(page);
    }

}