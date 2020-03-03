package io.dfjinxin.modules.storage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.storage.dao.UsrDataQueryCondDao;
import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;
import io.dfjinxin.modules.storage.service.UsrDataQueryCondService;


@Service("usrDataQueryCondService")
public class UsrDataQueryCondServiceImpl extends ServiceImpl<UsrDataQueryCondDao, UsrDataQueryCondEntity> implements UsrDataQueryCondService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsrDataQueryCondEntity> page = this.page(
                new Query<UsrDataQueryCondEntity>().getPage(params),
                new QueryWrapper<UsrDataQueryCondEntity>()
        );

        return new PageUtils(page);
    }

}