package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataExplTmplProjEntity;

import java.util.Map;

/**
 * 数据探查模板项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
public interface DataExplTmplProjService extends IService<DataExplTmplProjEntity> {

    PageUtils queryPage(Map<String, Object> params);

    int deleteByExp(Integer[] id);
    
}

