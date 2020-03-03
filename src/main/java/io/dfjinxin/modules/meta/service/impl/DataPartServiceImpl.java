package io.dfjinxin.modules.meta.service.impl;

import io.dfjinxin.common.utils.UserContenUtils;
import io.dfjinxin.modules.meta.dto.DataPartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.DataPartDao;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.service.DataPartService;


@Service("dataPartService")
public class DataPartServiceImpl extends ServiceImpl<DataPartDao, DataPartEntity> implements DataPartService {

    @Autowired
    private DataPartDao dataPartDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataPartEntity> page = this.page(
                new Query<DataPartEntity>().getPage(params),
                new QueryWrapper<DataPartEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<DataPartDto> selectPartByTnmt(Long tnmtid) {
        return dataPartDao.selectPartByTnmt(tnmtid);
    }
}