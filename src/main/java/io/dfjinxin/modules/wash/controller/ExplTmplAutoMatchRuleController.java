package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.wash.entity.ExplTmplAutoMatchRuleEntity;
import io.dfjinxin.modules.wash.service.ExplTmplAutoMatchRuleService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 探查模板自动匹配规则
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@RestController
@RequestMapping("wash/expltmplautomatchrule")
public class ExplTmplAutoMatchRuleController extends AbstractController {
    @Autowired
    private ExplTmplAutoMatchRuleService explTmplAutoMatchRuleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:expltmplautomatchrule:list")
    public R list(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        PageUtils page = explTmplAutoMatchRuleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{ruleid}")
    @RequiresPermissions("wash:expltmplautomatchrule:info")
    public R info(@PathVariable("ruleid") Integer ruleid){
		ExplTmplAutoMatchRuleEntity explTmplAutoMatchRule = explTmplAutoMatchRuleService.getById(ruleid);

        return R.ok().put("explTmplAutoMatchRule", explTmplAutoMatchRule);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:expltmplautomatchrule:save")
    public R save(@RequestBody ExplTmplAutoMatchRuleEntity explTmplAutoMatchRule){
        explTmplAutoMatchRule.setTnmtid(getTenantId().intValue());
        int cnt = existsNm(explTmplAutoMatchRule);
        if(cnt > 0){
            return R.error("您所输入的规则描述已存在，请重新修改");
        }
        if(StringUtils.isBlank(explTmplAutoMatchRule.getRuleDesc())){
            return R.error("规则描述不能为空");
        }
        if(StringUtils.isBlank(explTmplAutoMatchRule.getRuleExprs())){
            return R.error("规则表达式不能为空");
        }
        try{
            Pattern pattern = Pattern.compile(explTmplAutoMatchRule.getRuleExprs());
        }catch(PatternSyntaxException e){
            return R.error("规则表达式错误："+explTmplAutoMatchRule.getRuleExprs()+"，请填写正确的表达式");
        }
		explTmplAutoMatchRuleService.save(explTmplAutoMatchRule);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:expltmplautomatchrule:update")
    public R update(@RequestBody ExplTmplAutoMatchRuleEntity explTmplAutoMatchRule){
        explTmplAutoMatchRule.setTnmtid(getTenantId().intValue());
        int cnt = existsNm(explTmplAutoMatchRule);
        if(cnt > 0){
            return R.error("您所输入的规则描述已存在，请重新修改");
        }
        if(StringUtils.isBlank(explTmplAutoMatchRule.getRuleDesc())){
            return R.error("规则描述不能为空");
        }
        if(StringUtils.isBlank(explTmplAutoMatchRule.getRuleExprs())){
            return R.error("规则表达式不能为空");
        }
        try{
            Pattern pattern = Pattern.compile(explTmplAutoMatchRule.getRuleExprs());
        }catch(PatternSyntaxException e){
            return R.error("规则表达式错误："+explTmplAutoMatchRule.getRuleExprs()+"，请填写正确的表达式");
        }
		explTmplAutoMatchRuleService.updateById(explTmplAutoMatchRule);

        return R.ok();
    }
    private int existsNm(ExplTmplAutoMatchRuleEntity explTmplAutoMatchRule){
        int cnt = explTmplAutoMatchRuleService.count(
                new QueryWrapper<ExplTmplAutoMatchRuleEntity>()
                        .eq("Rule_Desc", explTmplAutoMatchRule.getRuleDesc())
                        .eq("Tnmtid", explTmplAutoMatchRule.getTnmtid())
                        .ne("Ruleid", explTmplAutoMatchRule.getRuleid())
        );
        return cnt;
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:expltmplautomatchrule:delete")
    public R delete(@RequestBody Integer[] ruleids){
		explTmplAutoMatchRuleService.removeByIds(Arrays.asList(ruleids));

        return R.ok();
    }

}
