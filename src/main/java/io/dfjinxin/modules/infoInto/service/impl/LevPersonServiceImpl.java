package io.dfjinxin.modules.infoInto.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.dao.LevPersonDao;
import io.dfjinxin.modules.infoInto.entity.LevPerson;
import io.dfjinxin.modules.infoInto.service.LevPersonService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/31 17:31
 * @Version: 1.0
 */
@Service("levPersonService")
public class LevPersonServiceImpl extends ServiceImpl<LevPersonDao, LevPerson> implements LevPersonService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page page = new Page((Integer) params.get("pageIndex"), (Integer) params.get("pageSize"));
        page = (Page) super.baseMapper.queryPage(page, params);
        return new PageUtils(page);
    }
}
