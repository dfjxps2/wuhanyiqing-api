package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataTblWashProjEntity;

import java.util.Map;

/**
 * 数据表清洗项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
public interface DataTblWashProjService extends IService<DataTblWashProjEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryRuleList(Map<String, Object> params);
}

