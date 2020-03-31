package io.dfjinxin.modules.outsider.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.modules.outsider.dao.LogDao;
import io.dfjinxin.modules.outsider.entity.LogEntity;
import io.dfjinxin.modules.outsider.service.LogService;

/**
 * 滞汉人员明细 服务层
 * @author zhujiazhou
 *
 */
@Service("leaveLogService")
public class LogServiceImpl extends ServiceImpl<LogDao, LogEntity> implements LogService{

}
