package io.dfjinxin.modules.storage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SdataDbDao {

    List<Map<String, Object>> executeSql(@Param("sql") String sql);

}
