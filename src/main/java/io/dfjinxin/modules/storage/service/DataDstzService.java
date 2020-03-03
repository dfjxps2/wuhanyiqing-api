package io.dfjinxin.modules.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.entity.DataDstzEntity;

import java.util.Map;

/**
 * 数据脱敏
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
public interface DataDstzService extends IService<DataDstzEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

