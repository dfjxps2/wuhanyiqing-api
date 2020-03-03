package io.dfjinxin.modules.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author z.h.c
 * @email ${email}
 * @date 2019-06-14 16:25:48
 */
public interface TagInfoService extends IService<TagInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<TagInfoEntity> queryFldTagInfoList(Long tnmtid);

    //add by SongChaoqun 获取表级对应关系数据
    List<TagInfoEntity> queryTblTagInfoList(Long tnmtid);

    Integer saveAndGetId(TagInfoEntity entity);

    List<TagInfoEntity> queryRuleInfoList(Long tenantId );

    TagInfoEntity selectEntityOne(TagInfoEntity entity);

    TagInfoEntity selectEntityOneById(Integer labelId);
}

