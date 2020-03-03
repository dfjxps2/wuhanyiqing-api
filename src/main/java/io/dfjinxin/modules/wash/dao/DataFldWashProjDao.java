package io.dfjinxin.modules.wash.dao;

import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 数据字段清洗项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@Repository
@Mapper
public interface DataFldWashProjDao extends BaseMapper<DataFldWashProjEntity> {
	
}
