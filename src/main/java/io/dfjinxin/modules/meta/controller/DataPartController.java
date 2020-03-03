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

import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.service.DataPartService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据分区
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@RestController
@RequestMapping("meta/datapart")
public class DataPartController {
    @Autowired
    private DataPartService dataPartService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:datapart:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataPartService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{partid}")
    @RequiresPermissions("meta:datapart:info")
    public R info(@PathVariable("partid") Integer partid){
		DataPartEntity dataPart = dataPartService.getById(partid);

        return R.ok().put("dataPart", dataPart);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:datapart:save")
    public R save(@RequestBody DataPartEntity dataPart){
		dataPartService.save(dataPart);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:datapart:update")
    public R update(@RequestBody DataPartEntity dataPart){
		dataPartService.updateById(dataPart);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:datapart:delete")
    public R delete(@RequestBody Integer[] partids){
		dataPartService.removeByIds(Arrays.asList(partids));

        return R.ok();
    }

}
