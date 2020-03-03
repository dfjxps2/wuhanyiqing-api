package io.dfjinxin.modules.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.tag.dao.TagDataTblDao;
import io.dfjinxin.modules.tag.dao.TagRuleDao;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import io.dfjinxin.modules.tag.entity.TagRuleEntity;
import io.dfjinxin.modules.tag.service.TagRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("tagRuleService")
public class TagRuleServiceImpl extends ServiceImpl<TagRuleDao, TagRuleEntity> implements TagRuleService {


    /**
     * 分页
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TagRuleEntity> page = this.page(
                new Query<TagRuleEntity>().getPage(params),
                new QueryWrapper<TagRuleEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * 列表
     *
     * @return
     */
    @Override
    public PageUtils queryTagRuleList(Map<String, Object> params) {

        int count = baseMapper.queryCountTagRule(params);
        int page = params.containsKey("page") ? Integer.parseInt(params.get("page").toString()) : 0;
        int pageSize = params.containsKey("limit") ? Integer.parseInt(params.get("limit").toString()) : 10;

        if (page > 0) {
            page = (page - 1) * pageSize;
        }
        params.put("start", page);
        params.put("pageSize", pageSize);
        List<TagRuleEntity> tagRuleEntityList = baseMapper.queryTagRuleList(params);
        return new PageUtils(tagRuleEntityList, count, pageSize, page);
    }

    /**
     * 规则增加
     *
     * @param tagRuleEntity
     */
    @Override
    public void saveTagRule(TagRuleEntity tagRuleEntity) {
        baseMapper.saveTagRule(tagRuleEntity);
    }

    @Override
    public TagRuleEntity selectTagRuleDescList(TagRuleEntity tagRuleEntity) {
        return baseMapper.selectTagRuleDescList(tagRuleEntity);
    }


}
