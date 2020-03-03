package io.dfjinxin.modules.wash.dao;

import io.dfjinxin.modules.wash.entity.DataChkProjEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据检查项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Repository
@Mapper
public interface DataChkProjDao extends BaseMapper<DataChkProjEntity> {

    DataChkProjEntity selectOneByName(@Param("chkProjNm")String chkProjNm);
	
}
