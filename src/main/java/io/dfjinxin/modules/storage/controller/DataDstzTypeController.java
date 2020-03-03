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

import io.dfjinxin.modules.storage.entity.DataDstzTypeEntity;
import io.dfjinxin.modules.storage.service.DataDstzTypeService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据脱敏类型
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
@RestController
@RequestMapping("/storage/datadstztype")
public class DataDstzTypeController {
    @Autowired
    private DataDstzTypeService dataDstzTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataDstzTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataDstzTypeid}")
    public R info(@PathVariable("dataDstzTypeid") Integer dataDstzTypeid){
		DataDstzTypeEntity dataDstzType = dataDstzTypeService.getById(dataDstzTypeid);

        return R.ok().put("dataDstzType", dataDstzType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DataDstzTypeEntity dataDstzType){
		dataDstzTypeService.save(dataDstzType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DataDstzTypeEntity dataDstzType){
		dataDstzTypeService.updateById(dataDstzType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] dataDstzTypeids){
		dataDstzTypeService.removeByIds(Arrays.asList(dataDstzTypeids));

        return R.ok();
    }

}
