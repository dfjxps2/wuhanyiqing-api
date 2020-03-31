package io.dfjinxin.modules.outsider.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.modules.outsider.dao.LeavePersonDao;
import io.dfjinxin.modules.outsider.entity.LeavePerson;
import io.dfjinxin.modules.outsider.service.LeavePersonService;

/**
 * 滞汉人员明细 服务层
 * @author zhujiazhou
 *
 */
@Service("leavePersonService")
public class LeavePersonServiceImpl extends ServiceImpl<LeavePersonDao, LeavePerson> implements LeavePersonService{

}
