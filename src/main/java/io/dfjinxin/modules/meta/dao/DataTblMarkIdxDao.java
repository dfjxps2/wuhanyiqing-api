package io.dfjinxin.modules.meta.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.meta.entity.DataTblMarkIdxEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据表标引
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataTblMarkIdxDao extends BaseMapper<DataTblMarkIdxEntity> {


    /**
     *
     * 列表展示
     * @return
     */
    IPage<Map<String, Object>> queryDataTblMarkIdx(Page page, @Param("param") Map<String,Object> params);





    /**
     *
     * 批量添加表和标签
     * @param dataTblId
     * @param labelList
     */
    void batchSaveTblLabel(@Param("dataTblId")Integer dataTblId, @Param("labelList")List<Integer> labelList);

    /**
     *
     * 批量删除表标签
     * @param dataTblId
     * @param labelList
     */
    void batchDeleteTblLabel(@Param("dataTblId")Integer dataTblId,@Param("labelList") List<Integer> labelList);

    Integer selectLabelUsedTimesByTable(@Param("labelId") Integer labelId);

    Integer selectLabelUsedTimesByLabelIds(@Param("labelIds") List<Integer> labelIdList);
}
