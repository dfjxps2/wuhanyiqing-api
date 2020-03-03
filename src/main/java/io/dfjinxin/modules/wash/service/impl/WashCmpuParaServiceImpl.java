package io.dfjinxin.modules.wash.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.WashCmpuParaDao;
import io.dfjinxin.modules.wash.entity.WashCmpuParaEntity;
import io.dfjinxin.modules.wash.service.WashCmpuParaService;


@Service("washCmpuParaService")
public class WashCmpuParaServiceImpl extends ServiceImpl<WashCmpuParaDao, WashCmpuParaEntity> implements WashCmpuParaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WashCmpuParaEntity> page = this.page(
                new Query<WashCmpuParaEntity>().getPage(params),
                new QueryWrapper<WashCmpuParaEntity>()
        );

        return new PageUtils(page);
    }

}