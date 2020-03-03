package io.dfjinxin.modules.meta.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.meta.entity.DataSrcEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据源
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataSrcDao extends BaseMapper<DataSrcEntity> {
    IPage<Map<String, Object>> listByTnmt(IPage<DataSrcEntity> pager, @Param("p")Map<String,Object> params);
}
