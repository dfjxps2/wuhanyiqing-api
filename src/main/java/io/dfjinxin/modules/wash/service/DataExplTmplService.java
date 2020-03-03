package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataExplTmplEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据探查模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
public interface DataExplTmplService extends IService<DataExplTmplEntity> {

    int add(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);

    /*根据模板名称查询*/
    PageUtils selectByExplId(Map<String, Object> params);

    /*根据模板id查询模板*/
    List<Map<String,Object>> getExplById(Map<String, Object> params);

    int updatePojo(Map<String, Object> params);

    int updateById(Map<String, Object> params);

}

