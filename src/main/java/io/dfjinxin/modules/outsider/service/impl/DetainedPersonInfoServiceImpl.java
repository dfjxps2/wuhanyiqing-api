package io.dfjinxin.modules.outsider.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.dfjinxin.modules.outsider.dao.DetainedPersonInfoDao;
import io.dfjinxin.modules.outsider.entity.DetainedPersonInfoEntity;
import io.dfjinxin.modules.outsider.service.DetainedPersonInfoService;

/**
 * 滞汉人员明细 服务层
 * @author zhujiazhou
 *
 */
@Service("detainedPersonInfoService")
public class DetainedPersonInfoServiceImpl extends ServiceImpl<DetainedPersonInfoDao, DetainedPersonInfoEntity> implements DetainedPersonInfoService{

}
