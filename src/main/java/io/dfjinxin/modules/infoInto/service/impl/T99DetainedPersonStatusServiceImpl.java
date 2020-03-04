package io.dfjinxin.modules.infoInto.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.infoInto.dao.T99DetainedPersonStatusDao;
import io.dfjinxin.modules.infoInto.entity.T99DetainedPersonStatusEntity;
import io.dfjinxin.modules.infoInto.service.T99DetainedPersonStatusService;


@Service("t99DetainedPersonStatusService")
public class T99DetainedPersonStatusServiceImpl extends ServiceImpl<T99DetainedPersonStatusDao, T99DetainedPersonStatusEntity> implements T99DetainedPersonStatusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T99DetainedPersonStatusEntity> page = this.page(
                new Query<T99DetainedPersonStatusEntity>().getPage(params),
                new QueryWrapper<T99DetainedPersonStatusEntity>()
        );

        return new PageUtils(page);
    }

}