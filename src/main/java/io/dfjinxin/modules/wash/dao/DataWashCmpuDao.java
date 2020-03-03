package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据清洗运算
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@Repository
@Mapper
public interface DataWashCmpuDao extends BaseMapper<DataWashCmpuEntity> {

    IPage<DataWashCmpuEntity> queryList(IPage<DataWashCmpuEntity> page, @Param("m") Map<String, Object> m);

    List<DataWashCmpuEntity> queryDataWashCmpuByDataWashCmpuType(Integer dataWashCmpuType);

    int getInfo(@Param("name") String name, @Param("id") Integer id,@Param("tnmtid")long tnmtid);
}
