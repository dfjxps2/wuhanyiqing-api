package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobResultEntity;

import java.util.Map;

/**
 * 已发布作业表级执行结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
public interface PrdDataProcJobResultService extends IService<PrdDataProcJobResultEntity> {

    PageUtils queryPage(Map<String, Object> params);


}

