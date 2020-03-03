package io.dfjinxin.modules.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * @Description: 标签类别信息service
 * @Author: z.h.c
 * @CreateDate: 2019/6/13 13:09
 * @Version: 1.0
 */

public interface TagTypeService extends IService<TagTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List queryTagTypeList(Long tenantId);

    Integer saveAndGetId(TagTypeEntity entity);

    void delTagAndLabel(List tagIdList, List labelIdList);

    TagTypeEntity selectEntityOne(Integer labelCatgid);

    TagTypeEntity selectEntityByWrapper(TagTypeEntity entity);
}

