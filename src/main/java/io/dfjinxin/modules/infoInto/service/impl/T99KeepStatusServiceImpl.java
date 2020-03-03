package io.dfjinxin.modules.infoInto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.infoInto.dao.T99KeepStatusDao;
import io.dfjinxin.modules.infoInto.entity.T99KeepStatusEntity;
import io.dfjinxin.modules.infoInto.service.T99KeepStatusService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("t99KeepStatusService")
public class T99KeepStatusServiceImpl extends ServiceImpl<T99KeepStatusDao, T99KeepStatusEntity> implements T99KeepStatusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T99KeepStatusEntity> page = this.page(
                new Query<T99KeepStatusEntity>().getPage(params),
                new QueryWrapper<T99KeepStatusEntity>()
        );

        return new PageUtils(page);
    }

}
