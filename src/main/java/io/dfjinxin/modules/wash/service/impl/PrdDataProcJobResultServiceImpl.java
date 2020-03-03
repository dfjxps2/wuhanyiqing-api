package io.dfjinxin.modules.wash.service.impl;

import io.dfjinxin.modules.wash.dao.PrdDataProcJobResultDao;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobResultEntity;
import io.dfjinxin.modules.wash.service.PrdDataProcJobResultService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;


@Service("prdDataProcJobResultService")
public class PrdDataProcJobResultServiceImpl extends ServiceImpl<PrdDataProcJobResultDao, PrdDataProcJobResultEntity> implements PrdDataProcJobResultService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrdDataProcJobResultEntity> page = this.page(
                new Query<PrdDataProcJobResultEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

}