package io.dfjinxin.modules.wash.dao;

import io.dfjinxin.modules.wash.entity.DataExplTmplProjEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据探查模板项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Repository
@Mapper
public interface DataExplTmplProjDao extends BaseMapper<DataExplTmplProjEntity> {
    int deleteByExp(@Param("id") Integer[] id);
}
