package io.dfjinxin.modules.outsider.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.modules.outsider.dao.LogDao;
import io.dfjinxin.modules.outsider.entity.LogEntity;
import io.dfjinxin.modules.outsider.service.LogService;

import java.util.Map;

/**
 * 滞汉人员明细 服务层
 * @author zhujiazhou
 *
 */
@Service("leaveLogService")
public class LogServiceImpl extends ServiceImpl<LogDao, LogEntity> implements LogService{

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page page = new Page((Integer) params.get("pageIndex"), (Integer) params.get("pageSize"));
        page = (Page) super.baseMapper.queryPage(page, params);
        return new PageUtils(page);
    }
}
