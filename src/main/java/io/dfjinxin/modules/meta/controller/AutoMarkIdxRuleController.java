package io.dfjinxin.modules.meta.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.meta.entity.AutoMarkIdxRuleEntity;
import io.dfjinxin.modules.meta.service.AutoMarkIdxRuleService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 自动标引规则
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
@RestController
@RequestMapping("meta/automarkidxrule")
public class AutoMarkIdxRuleController {
    @Autowired
    private AutoMarkIdxRuleService autoMarkIdxRuleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:automarkidxrule:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = autoMarkIdxRuleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ruleid}")
    @RequiresPermissions("meta:automarkidxrule:info")
    public R info(@PathVariable("ruleid") Integer ruleid){
		AutoMarkIdxRuleEntity autoMarkIdxRule = autoMarkIdxRuleService.getById(ruleid);

        return R.ok().put("autoMarkIdxRule", autoMarkIdxRule);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:automarkidxrule:save")
    public R save(@RequestBody AutoMarkIdxRuleEntity autoMarkIdxRule){
		autoMarkIdxRuleService.save(autoMarkIdxRule);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:automarkidxrule:update")
    public R update(@RequestBody AutoMarkIdxRuleEntity autoMarkIdxRule){
		autoMarkIdxRuleService.updateById(autoMarkIdxRule);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:automarkidxrule:delete")
    public R delete(@RequestBody Integer[] ruleids){
		autoMarkIdxRuleService.removeByIds(Arrays.asList(ruleids));

        return R.ok();
    }

}
