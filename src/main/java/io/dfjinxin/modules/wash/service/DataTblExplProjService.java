package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataTblExplProjEntity;

import java.util.Map;

/**
 * 数据表探查项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
public interface DataTblExplProjService extends IService<DataTblExplProjEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

