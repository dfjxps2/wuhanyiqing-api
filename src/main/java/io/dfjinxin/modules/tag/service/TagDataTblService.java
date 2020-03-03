package io.dfjinxin.modules.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-06-16 15:18:37
 */
public interface TagDataTblService extends IService<TagDataTblEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryByCriteria(DesenForm desenForm, Long tenantId);

    PageUtils getTblAndFldInfoListbyTblIds(DesenForm desenForm, Integer tenantId);

    PageUtils queryLabelViewInfoByPage(DesenForm desenForm, Integer tenantId);
}

