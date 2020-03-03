package io.dfjinxin.modules.meta.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据字段
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
@Mapper
@Repository
public interface DataFldDao extends BaseMapper<DataFldEntity> {

    Integer getFldsCountPageByDataTblId(@Param("param") DesenForm desenForm);

    List<DataFldEntity> getFldsListPageByDataTblId(@Param("param") DesenForm desenForm);

    Integer queryDataFldMarkIdxCount(@Param("param") DesenForm desenForm);

    List<DataFldEntity> queryDataFldMarkIdxList(@Param("param") DesenForm desenForm);


}
