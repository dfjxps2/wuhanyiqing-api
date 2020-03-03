package io.dfjinxin.modules.wash.service.impl;

import io.dfjinxin.modules.wash.dao.PrdDataFldWashProjDao;
import io.dfjinxin.modules.wash.entity.PrdDataFldWashProjEntity;
import io.dfjinxin.modules.wash.service.PrdDataFldWashProjService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;


@Service("prdDataFldWashProjService")
public class PrdDataFldWashProjServiceImpl extends ServiceImpl<PrdDataFldWashProjDao, PrdDataFldWashProjEntity> implements PrdDataFldWashProjService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrdDataFldWashProjEntity> page = this.page(
                new Query<PrdDataFldWashProjEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}