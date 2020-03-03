package io.dfjinxin.modules.tag.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据表
 * 
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-06-16 15:18:37
 */
@Repository
@Mapper
public interface TagDataTblDao extends BaseMapper<TagDataTblEntity> {

    List<TagDataTblEntity> queryListByCriteria(@Param("param") DesenForm desenForm);

    Integer queryCountByCriteria(@Param("param") DesenForm desenForm);

//    TagDataTblEntity queryByTblId(Integer dataTblid);

}
