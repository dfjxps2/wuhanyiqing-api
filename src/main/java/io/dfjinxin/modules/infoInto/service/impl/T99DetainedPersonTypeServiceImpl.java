package io.dfjinxin.modules.infoInto.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.infoInto.dao.T99DetainedPersonTypeDao;
import io.dfjinxin.modules.infoInto.entity.T99DetainedPersonTypeEntity;
import io.dfjinxin.modules.infoInto.service.T99DetainedPersonTypeService;


@Service("t99DetainedPersonTypeService")
public class T99DetainedPersonTypeServiceImpl extends ServiceImpl<T99DetainedPersonTypeDao, T99DetainedPersonTypeEntity> implements T99DetainedPersonTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T99DetainedPersonTypeEntity> page = this.page(
                new Query<T99DetainedPersonTypeEntity>().getPage(params),
                new QueryWrapper<T99DetainedPersonTypeEntity>()
        );

        return new PageUtils(page);
    }

}