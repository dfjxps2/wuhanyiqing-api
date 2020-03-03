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

import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.service.DataFldService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据字段
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
@RestController
@RequestMapping("meta/datafld")
public class DataFldController {
    @Autowired
    private DataFldService dataFldService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:datafld:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataFldService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fldid}")
    @RequiresPermissions("meta:datafld:info")
    public R info(@PathVariable("fldid") Integer fldid){
		DataFldEntity dataFld = dataFldService.getById(fldid);

        return R.ok().put("dataFld", dataFld);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:datafld:save")
    public R save(@RequestBody DataFldEntity dataFld){
		dataFldService.save(dataFld);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:datafld:update")
    public R update(@RequestBody DataFldEntity dataFld){
		dataFldService.updateById(dataFld);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:datafld:delete")
    public R delete(@RequestBody Integer[] fldids){
		dataFldService.removeByIds(Arrays.asList(fldids));

        return R.ok();
    }

}
