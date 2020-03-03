package io.dfjinxin.modules.meta.dao;

import io.dfjinxin.modules.meta.dto.DataPartDto;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据分区
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataPartDao extends BaseMapper<DataPartEntity> {
    List<DataPartDto> selectPartByTnmt(@Param("tnmtid")Long tnmtid);
}
