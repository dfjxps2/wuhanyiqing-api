package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobResultEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 已发布作业表级执行结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Repository
@Mapper
public interface PrdDataProcJobResultDao extends BaseMapper<PrdDataProcJobResultEntity> {
	
}
