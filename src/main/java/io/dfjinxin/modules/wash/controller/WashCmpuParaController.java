package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.wash.entity.WashCmpuParaEntity;
import io.dfjinxin.modules.wash.service.WashCmpuParaService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 清洗运算参数
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@RestController
@RequestMapping("wash/washcmpupara")
public class WashCmpuParaController {
    @Autowired
    private WashCmpuParaService washCmpuParaService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:washcmpupara:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = washCmpuParaService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{paraid}")
    @RequiresPermissions("wash:washcmpupara:info")
    public R info(@PathVariable("paraid") Integer paraid){
		WashCmpuParaEntity washCmpuPara = washCmpuParaService.getById(paraid);

        return R.ok().put("washCmpuPara", washCmpuPara);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:washcmpupara:save")
    public R save(@RequestBody WashCmpuParaEntity washCmpuPara){
		washCmpuParaService.save(washCmpuPara);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:washcmpupara:update")
    public R update(@RequestBody WashCmpuParaEntity washCmpuPara){
		washCmpuParaService.updateById(washCmpuPara);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:washcmpupara:delete")
    public R delete(@RequestBody Integer[] paraids){
		washCmpuParaService.removeByIds(Arrays.asList(paraids));

        return R.ok();
    }

}
