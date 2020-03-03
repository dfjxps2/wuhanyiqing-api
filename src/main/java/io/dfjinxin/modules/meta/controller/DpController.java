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

import io.dfjinxin.modules.meta.entity.DpEntity;
import io.dfjinxin.modules.meta.service.DpService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据片
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@RestController
@RequestMapping("meta/dp")
public class DpController {
    @Autowired
    private DpService dpService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:dp:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dpService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listAll")
    public R listAll(@RequestParam Map<String, Object> params){
        PageUtils page = dpService.queryAll(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dpid}")
    @RequiresPermissions("meta:dp:info")
    public R info(@PathVariable("dpid") String dpid){
		DpEntity dp = dpService.getById(dpid);

        return R.ok().put("dp", dp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:dp:save")
    public R save(@RequestBody DpEntity dp){
		dpService.save(dp);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:dp:update")
    public R update(@RequestBody DpEntity dp){
		dpService.updateById(dp);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:dp:delete")
    public R delete(@RequestBody String[] dpids){
		dpService.removeByIds(Arrays.asList(dpids));

        return R.ok();
    }

}
