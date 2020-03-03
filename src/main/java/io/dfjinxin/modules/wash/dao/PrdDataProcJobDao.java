package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 已发布数据处理作业
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Repository
@Mapper
public interface PrdDataProcJobDao extends BaseMapper<PrdDataProcJobEntity> {

    void updateJobById(PrdDataProcJobEntity procJobEntity);
	
}
