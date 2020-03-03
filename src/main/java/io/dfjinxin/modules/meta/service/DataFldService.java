package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DataFldEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字段
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
public interface DataFldService extends IService<DataFldEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

