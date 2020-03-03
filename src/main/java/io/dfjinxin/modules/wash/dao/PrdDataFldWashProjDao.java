package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.wash.entity.PrdDataFldWashProjEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 已发布数据字段清洗项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-27 14:38:06
 */
@Repository
@Mapper
public interface PrdDataFldWashProjDao extends BaseMapper<PrdDataFldWashProjEntity> {
	
}
