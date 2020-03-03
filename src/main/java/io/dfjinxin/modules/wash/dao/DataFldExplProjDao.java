package io.dfjinxin.modules.wash.dao;

import io.dfjinxin.modules.wash.entity.DataFldExplProjEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 数据字段探查项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Repository
@Mapper
public interface DataFldExplProjDao extends BaseMapper<DataFldExplProjEntity> {
	
}
