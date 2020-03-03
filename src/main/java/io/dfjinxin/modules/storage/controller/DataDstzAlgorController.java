package io.dfjinxin.modules.storage.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.dfjinxin.modules.storage.entity.DataDstzAlgorEntity;
import io.dfjinxin.modules.storage.service.DataDstzAlgorService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据脱敏算法
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
@RestController
@RequestMapping("/storage/datadstzalgor")
public class DataDstzAlgorController {
    @Autowired
    private DataDstzAlgorService dataDstzAlgorService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataDstzAlgorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataDstzAlgorid}")
    public R info(@PathVariable("dataDstzAlgorid") Integer dataDstzAlgorid){
		DataDstzAlgorEntity dataDstzAlgor = dataDstzAlgorService.getById(dataDstzAlgorid);

        return R.ok().put("dataDstzAlgor", dataDstzAlgor);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DataDstzAlgorEntity dataDstzAlgor){
		dataDstzAlgorService.save(dataDstzAlgor);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DataDstzAlgorEntity dataDstzAlgor){
		dataDstzAlgorService.updateById(dataDstzAlgor);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] dataDstzAlgorids){
		dataDstzAlgorService.removeByIds(Arrays.asList(dataDstzAlgorids));

        return R.ok();
    }

    @GetMapping("/all")
    public R all(){
        return R.ok().put("data", dataDstzAlgorService.queryDataDstzAlgorAll());
    }
}
