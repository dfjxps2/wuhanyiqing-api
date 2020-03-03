package io.dfjinxin.modules.meta.dao;

import io.dfjinxin.modules.meta.entity.DbEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.dto.DateQueryItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据库
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@Mapper
@Repository
public interface DbDao extends BaseMapper<DbEntity> {

//    List<DbEntity> queryDbListByTnmt(@Param("tnmtid")Long tnmtid);

    List<DbEntity> queryDbsRestrict(Map<String, Object> map);

    List<DbEntity> queryDBListByPartid(@Param("partid")Integer partid);

    Map<String, Object> queryDBByCd(@Param("tnmtid")Long tnmtid, @Param("dataType")String dataType, @Param("dbid")Integer dbid);

    DateQueryItemDto queryDBUBytblid(@Param("tblid")Integer tblid);
}
