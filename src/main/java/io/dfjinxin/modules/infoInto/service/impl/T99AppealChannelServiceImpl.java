package io.dfjinxin.modules.infoInto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.infoInto.dao.T99AppealChannelDao;
import io.dfjinxin.modules.infoInto.entity.T99AppealChannelEntity;
import io.dfjinxin.modules.infoInto.service.T99AppealChannelService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("t99AppealChannelService")
public class T99AppealChannelServiceImpl extends ServiceImpl<T99AppealChannelDao, T99AppealChannelEntity> implements T99AppealChannelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<T99AppealChannelEntity> page = this.page(
                new Query<T99AppealChannelEntity>().getPage(params),
                new QueryWrapper<T99AppealChannelEntity>()
        );

        return new PageUtils(page);
    }

}
