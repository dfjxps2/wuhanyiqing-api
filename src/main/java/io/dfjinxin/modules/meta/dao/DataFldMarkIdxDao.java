package io.dfjinxin.modules.meta.dao;

import io.dfjinxin.modules.meta.entity.DataFldMarkIdxEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.dto.DesenForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字段标引
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataFldMarkIdxDao extends BaseMapper<DataFldMarkIdxEntity> {

    void batchAddFldLabel(@Param("fldId") Integer fldId, @Param("labelList") List<Integer> labelList);

    void batchDelFldLabel(@Param("fldId") Integer fldId, @Param("labelList") List<Integer> labelList);

    Integer selectLabelUsedTimesByFld(@Param("labelId") Integer labelId);

    Integer selectLabelUsedTimesByLabelIds(@Param("labelIds") List<Integer> labelIds);

//    List<DataFldMarkIdxEntity> queryListByLabelId(@Param("param") DesenForm labelId);

//    Integer countDataFldMarkIdxEntity(@Param("param") DesenForm desenForm);
}
