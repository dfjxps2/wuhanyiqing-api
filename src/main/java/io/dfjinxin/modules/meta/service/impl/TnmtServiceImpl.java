package io.dfjinxin.modules.meta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.TnmtDao;
import io.dfjinxin.modules.meta.entity.TnmtEntity;
import io.dfjinxin.modules.meta.service.TnmtService;


@Service("tnmtService")
public class TnmtServiceImpl extends ServiceImpl<TnmtDao, TnmtEntity> implements TnmtService {

    @Autowired
    private TnmtDao tnmtDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TnmtEntity> page = this.page(
                new Query<TnmtEntity>().getPage(params),
                new QueryWrapper<TnmtEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public TnmtEntity getTnmtByDepart(String departId) {
        TnmtEntity tnmtEntity = tnmtDao.getTnmtByDepart(departId);
        return tnmtEntity;
    }

}