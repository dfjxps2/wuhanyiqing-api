package io.dfjinxin.modules.meta.dao;

import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.dto.PartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据分区类型
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataPartTypeDao extends BaseMapper<DataPartTypeEntity> {

    List<DataPartTypeEntity> queryDataPartByTnmtid(@Param("tnmtid") Integer tnmtid);

    List<PartDto> queryDataPartRestrict(Map<String, Object> map);
}
