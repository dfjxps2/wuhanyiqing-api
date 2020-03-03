package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.DataTblWashProjEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
/**
 * 数据表清洗项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Repository
@Mapper
public interface DataTblWashProjDao extends BaseMapper<DataTblWashProjEntity> {
    /**
     * 清洗规则列表
     * @return
     */
    IPage<Map<String, Object>> queryRuleList(Page page, @Param("m") Map<String, Object> m);
}
