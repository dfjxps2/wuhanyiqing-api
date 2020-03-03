package io.dfjinxin.modules.tag.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: z.h.c
 * @CreateDate: 2019/6/13 14:37
 * @Version: 1.0
 */
@Repository
@Mapper
public interface TagTypeDao extends BaseMapper<TagTypeEntity> {

    List queryTagTypeList(@Param("tenantId") Long tenantId);

    Integer saveAndGetId(TagTypeEntity entity);
}
