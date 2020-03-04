package io.dfjinxin.modules.infoInto.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.infoInto.dao.T99AppealTypeDao;
import io.dfjinxin.modules.infoInto.entity.T99AppealTypeEntity;
import io.dfjinxin.modules.infoInto.service.T99AppealTypeService;


@Service("t99AppealTypeService")
public class T99AppealTypeServiceImpl extends ServiceImpl<T99AppealTypeDao, T99AppealTypeEntity> implements T99AppealTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T99AppealTypeEntity> page = this.page(
                new Query<T99AppealTypeEntity>().getPage(params),
                new QueryWrapper<T99AppealTypeEntity>()
        );

        return new PageUtils(page);
    }

}