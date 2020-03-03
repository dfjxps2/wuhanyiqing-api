package io.dfjinxin.modules.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.entity.DataDstzTypeEntity;

import java.util.Map;

/**
 * 数据脱敏类型
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
public interface DataDstzTypeService extends IService<DataDstzTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

