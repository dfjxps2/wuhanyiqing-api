package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DataSrcEntity;

import java.util.Map;

/**
 * 数据源
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
public interface DataSrcService extends IService<DataSrcEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils listByTnmt(Map<String,Object> params);
}

