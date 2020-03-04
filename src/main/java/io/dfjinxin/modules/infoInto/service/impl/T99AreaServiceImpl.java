package io.dfjinxin.modules.infoInto.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.infoInto.dao.T99AreaDao;
import io.dfjinxin.modules.infoInto.entity.T99AreaEntity;
import io.dfjinxin.modules.infoInto.service.T99AreaService;


@Service("t99AreaService")
public class T99AreaServiceImpl extends ServiceImpl<T99AreaDao, T99AreaEntity> implements T99AreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T99AreaEntity> page = this.page(
                new Query<T99AreaEntity>().getPage(params),
                new QueryWrapper<T99AreaEntity>()
        );

        return new PageUtils(page);
    }

}