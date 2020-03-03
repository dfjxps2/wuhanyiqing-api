package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.FldExplResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 字段探查结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Repository
@Mapper
public interface FldExplResultDao extends BaseMapper<FldExplResultEntity> {
    /**
     * 探查报告
     * @return
     */
    IPage<Map<String, Object>> expReport(Page page, @Param("p") Map<String, Object> p);

    List<Map<String,Object>> expInfo(Object data_tblid);

    List<Map<String,Object>> expProj(Object data_tblid);

    List<Map<String,Object>> expReport(@Param("p") Map<String, Object> p);
}
