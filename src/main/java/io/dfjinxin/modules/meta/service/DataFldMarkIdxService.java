package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DataFldMarkIdxEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据字段标引
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
public interface DataFldMarkIdxService extends IService<DataFldMarkIdxEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void batchAddFldLabel(List<Integer> fldList, List<Integer> labelList);

    void batchDelFldLabel(List<Integer> fldList, List<Integer> labelList);

    /**
     * 查询标签是否被字段标引过
     *
     * @param labelId
     * @return
     */
    Integer selectCountByLabelId(Integer labelId);

    Integer selectFldLabCountByLabelIds(List<Integer> labelIdList);
}

