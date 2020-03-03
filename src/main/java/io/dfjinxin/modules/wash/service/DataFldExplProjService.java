package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataFldExplProjEntity;

import java.util.Map;

/**
 * 数据字段探查项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
public interface DataFldExplProjService extends IService<DataFldExplProjEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

