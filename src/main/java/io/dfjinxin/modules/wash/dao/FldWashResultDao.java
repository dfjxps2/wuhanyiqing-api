package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.FldWashResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 字段清洗结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Repository
@Mapper
public interface FldWashResultDao extends BaseMapper<FldWashResultEntity> {
    /**
     * 清洗报告
     * @return
     */
    IPage<Map<String, Object>> washReport(Page page, @Param("p") Map<String, Object> p);

    List<Map<String,Object>> washInfo(Object data_tblid);

    List<Map<String,Object>> washProj(Object data_tblid);

    List<Map<String,Object>> washReport(@Param("p") Map<String, Object> p);

}
