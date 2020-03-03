package io.dfjinxin.modules.tag.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.tag.entity.TagRuleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TagRuleDao extends BaseMapper<TagRuleEntity> {


    /**
     * 列表
     * @return
     */
    List<TagRuleEntity> queryTagRuleList(Map<String,Object> params);


    /**
     * 总条数
     * @param params
     * @return
     */
    Integer queryCountTagRule(Map<String,Object> params);

    /**
     * 规则增加
     * @param tagRuleEntity
     */
    void saveTagRule(TagRuleEntity tagRuleEntity);



    /**
     * 判断添加规则名称是否重复
     * @param ruleDesc
     * @return
     */
    TagRuleEntity selectTagRuleDescList(TagRuleEntity tagRuleEntity);

}
