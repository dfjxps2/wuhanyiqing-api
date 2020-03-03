package io.dfjinxin.modules.tag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.tag.entity.TagRuleEntity;

import java.util.List;
import java.util.Map;

public interface TagRuleService extends IService<TagRuleEntity> {

    /**
     * 分页
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 列表
     * @return
     */
    PageUtils queryTagRuleList(Map<String, Object> params);

    /**
     * 规则增加
     * @param tagRuleEntity
     */
    void saveTagRule(TagRuleEntity tagRuleEntity);


    /**
     * 判断数据库中是否有重复规则名称
     * @param tagRuleEntity
     * @return
     */
    TagRuleEntity selectTagRuleDescList(TagRuleEntity tagRuleEntity);
}
