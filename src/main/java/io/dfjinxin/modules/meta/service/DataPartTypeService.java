package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据分区类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
public interface DataPartTypeService extends IService<DataPartTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List queryDataPartList(Long tnmtid);
}

