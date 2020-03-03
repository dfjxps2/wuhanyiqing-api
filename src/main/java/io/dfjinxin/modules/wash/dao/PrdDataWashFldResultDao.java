package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.PrdDataWashFldResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 已发布作业字段清洗结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Repository
@Mapper
public interface PrdDataWashFldResultDao extends BaseMapper<PrdDataWashFldResultEntity> {

    List<PrdDataWashFldResultEntity> queryDataByJobid(Integer jobid);

    /**
     * 清洗报告
     * @return
     */
    IPage<Map<String, Object>> washReport(Page page, @Param("p") Map<String, Object> p);

    List<Map<String,Object>> washInfo(@Param("p") Map<String, Object> p);

    List<Map<String,Object>> washProj(@Param("p") Map<String, Object> p);

    List<Map<String,Object>> dataSrcList(@Param("p") Map<String, Object> p);


    List<Map<String,Object>> washReport(@Param("p") Map<String, Object> p);

    List<Map<String,Object>> tableFld(@Param("p") Map<String, Object> p);

    IPage<Map<String, Object>> fieldProInfo(Page page, @Param("p") Map<String, Object> p);

    IPage<Map<String, Object>> tableReport(Page page, @Param("p") Map<String, Object> p);

    IPage<Map<String, Object>> tableReportInfo(Page page, @Param("p") Map<String, Object> p);

    IPage<Map<String, Object>> tableProInfo(Page page, @Param("p") Map<String, Object> p);

    String lastExecDate(@Param("p") Map<String, Object> p);
}
