package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataTblMarkIdxEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据表标引
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
public interface DataTblMarkIdxService extends IService<DataTblMarkIdxEntity> {

    /**
     * 分页
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);


    /**
     * 列表展示
     *
     * @return
     */
    PageUtils queryDataTblMarkIdx(Map<String, Object> params);


    /**
     * 批量添加表和标签
     *
     * @param tblList
     * @param labelList
     */
    void batchSaveTblLabel(List<Integer> tblList, List<Integer> labelList);

    /**
     * 批量删除表标签
     *
     * @param tblList
     * @param labelList
     */
    void batchDeleteTblLabel(List<Integer> tblList, List<Integer> labelList);

    /**
     * 查询标签是否被表标引过
     *
     * @param labelId
     * @return
     */
    Integer selectCountByLabelId(Integer labelId);

    Integer selectTblLabCountByLabelIds(List<Integer> labelIdList);
}
