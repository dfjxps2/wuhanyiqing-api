package io.dfjinxin.modules.storage.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.storage.entity.DataDstzEntity;
import io.dfjinxin.modules.storage.service.DataDstzService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据脱敏
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
@RestController
@RequestMapping("/storage/datadstz")
public class DataDstzController {
    @Autowired
    private DataDstzService dataDstzService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataDstzService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fldid}")
    public R info(@PathVariable("fldid") Integer fldid){
		DataDstzEntity dataDstz = dataDstzService.getById(fldid);

        return R.ok().put("dataDstz", dataDstz);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DataDstzEntity dataDstz){
		dataDstzService.save(dataDstz);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DataDstzEntity dataDstz){
		dataDstzService.updateById(dataDstz);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] fldids){
		dataDstzService.removeByIds(Arrays.asList(fldids));

        return R.ok();
    }

}
