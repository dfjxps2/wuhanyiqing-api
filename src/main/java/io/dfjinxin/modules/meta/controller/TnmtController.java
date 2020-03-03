package io.dfjinxin.modules.meta.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.meta.entity.TnmtEntity;
import io.dfjinxin.modules.meta.service.TnmtService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 租户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@RestController
@RequestMapping("meta/tnmt")
public class TnmtController extends AbstractController {
    @Autowired
    private TnmtService tnmtService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:tnmt:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tnmtService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tnmtid}")
    @RequiresPermissions("meta:tnmt:info")
    public R info(@PathVariable("tnmtid") Integer tnmtid){
		TnmtEntity tnmt = tnmtService.getById(tnmtid);

        return R.ok().put("tnmt", tnmt);
    }
    @RequestMapping("/current")
    public R current(){
        Integer tnmtid = getTenantId().intValue();
        TnmtEntity tnmt = tnmtService.getById(tnmtid);

        return R.ok().put("tnmt", tnmt);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:tnmt:save")
    public R save(@RequestBody TnmtEntity tnmt){
		tnmtService.save(tnmt);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:tnmt:update")
    public R update(@RequestBody TnmtEntity tnmt){
		tnmtService.updateById(tnmt);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:tnmt:delete")
    public R delete(@RequestBody Integer[] tnmtids){
		tnmtService.removeByIds(Arrays.asList(tnmtids));

        return R.ok();
    }

}
