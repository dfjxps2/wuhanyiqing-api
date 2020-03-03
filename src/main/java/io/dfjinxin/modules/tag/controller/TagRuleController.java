package io.dfjinxin.modules.tag.controller;


import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.utils.UserContenUtils;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.tag.entity.TagRuleEntity;
import io.dfjinxin.modules.tag.service.TagRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 标签规则管理controller
 */
@RestController
@RequestMapping("tag/rule")
public class TagRuleController extends AbstractController {


    @Autowired
    private TagRuleService tagRuleService;

    /**
     * 分页
     */
    @RequestMapping("/queryTagRuleList")
    public R queryTagRule(@RequestParam Map<String, Object> params) {
        //为在表标签管理中添加租户条件而写
        params.put("tnmtid", this.getTenantId());
        PageUtils page = tagRuleService.queryTagRuleList(params);
        return R.ok().put("page", page);
    }





    /**
     * 保存
     */
    @RequestMapping("/saveTagRule")
    public R saveTagRule(@RequestBody TagRuleEntity tagRuleEntity){
        //为添加租户条件而写
        tagRuleEntity.setTnmtid(this.getTenantId().intValue());
        //判断数据库中是否有重复规则名称
        TagRuleEntity ruleDesclist = tagRuleService.selectTagRuleDescList(tagRuleEntity);
        if (ruleDesclist != null) {
            return R.error(500,"该规则名称已存在，请重新输入");
        }
        tagRuleService.saveTagRule(tagRuleEntity);
        return R.ok();

    }



    /**
     * 删除
     */
    @RequestMapping("/deleteTagRule")
    public R delete(@RequestBody Integer[] ruleids){
        tagRuleService.removeByIds(Arrays.asList(ruleids));
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/updateTagRule")
    public R updateTagRule(@RequestBody TagRuleEntity tagRuleEntity){
        //为添加租户条件而写
        tagRuleEntity.setTnmtid(this.getTenantId().intValue());
        //判断数据库中是否有重复规则名称
        TagRuleEntity ruleDesclist = tagRuleService.selectTagRuleDescList(tagRuleEntity);
        if (ruleDesclist != null && tagRuleEntity.getRuleid().intValue() != ruleDesclist.getRuleid().intValue()) {
            return R.error(500,"该规则名称已存在，请重新输入");
        }
        tagRuleService.updateById(tagRuleEntity);
        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ruleid}")
    public R info(@PathVariable("ruleid") String ruleid){
        TagRuleEntity tagRuleEntity = tagRuleService.getById(ruleid);

        return R.ok().put("tagRuleEntity", tagRuleEntity);
    }




}
