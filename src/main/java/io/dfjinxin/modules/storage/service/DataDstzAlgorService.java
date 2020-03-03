package io.dfjinxin.modules.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.entity.DataDstzAlgorEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据脱敏算法
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
public interface DataDstzAlgorService extends IService<DataDstzAlgorEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DataDstzAlgorEntity> queryDataDstzAlgorAll();
}

