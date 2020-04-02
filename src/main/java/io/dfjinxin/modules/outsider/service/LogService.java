package io.dfjinxin.modules.outsider.service;

import com.baomidou.mybatisplus.extension.service.IService;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.outsider.entity.LogEntity;

import java.util.Map;

public interface LogService  extends IService<LogEntity>{

    PageUtils queryPage(Map<String, Object> params);
}
