package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.dto.DataPartDto;
import io.dfjinxin.modules.meta.entity.DataPartEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据分区
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
public interface DataPartService extends IService<DataPartEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<DataPartDto> selectPartByTnmt(Long tnmtid);
}

