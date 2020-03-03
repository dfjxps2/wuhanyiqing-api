package io.dfjinxin.modules.wash.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataExplTmplProjDao;
import io.dfjinxin.modules.wash.entity.DataExplTmplProjEntity;
import io.dfjinxin.modules.wash.service.DataExplTmplProjService;


@Service("dataExplTmplProjService")
public class DataExplTmplProjServiceImpl extends ServiceImpl<DataExplTmplProjDao, DataExplTmplProjEntity> implements DataExplTmplProjService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataExplTmplProjEntity> page = this.page(
                new Query<DataExplTmplProjEntity>().getPage(params),
                new QueryWrapper<DataExplTmplProjEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int deleteByExp(Integer[] id) {

        return  baseMapper.deleteByExp(id);
    }

}