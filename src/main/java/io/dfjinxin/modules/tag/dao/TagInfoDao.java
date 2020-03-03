package io.dfjinxin.modules.tag.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author z.h.c
 * @email ${email}
 * @date 2019-06-14 16:25:48
 */
@Repository
@Mapper
public interface TagInfoDao extends BaseMapper<TagInfoEntity> {

    List<TagInfoEntity> queryFldTagInfoList(@Param("tenantId") Long tenantId);

    Integer saveAndGetId(TagInfoEntity entity);

    List<TagInfoEntity> queryTblTagInfoList(@Param("tenantId") Long tenantId);

    List<TagInfoEntity> queryLabelsByFldId(@Param("fldid") Integer fidid,
                                           @Param("tenantId") Integer tenantId);

    /**
     * 有多少规则表被标签使用
     * @return
     */
    List<TagInfoEntity> queryRuleInfoList(@Param("tenantId") Long tenantId);

}
