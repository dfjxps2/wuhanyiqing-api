package io.dfjinxin.modules.meta.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.meta.entity.DataFldMarkIdxEntity;
import io.dfjinxin.modules.meta.service.DataFldMarkIdxService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;


/**
 * 数据字段标引
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@RestController
@RequestMapping("meta/datafldmarkidx")
public class DataFldMarkIdxController {
    @Autowired
    private DataFldMarkIdxService dataFldMarkIdxService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:datafldmarkidx:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = dataFldMarkIdxService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fldid}")
    @RequiresPermissions("meta:datafldmarkidx:info")
    public R info(@PathVariable("fldid") Integer fldid) {
        DataFldMarkIdxEntity dataFldMarkIdx = dataFldMarkIdxService.getById(fldid);

        return R.ok().put("dataFldMarkIdx", dataFldMarkIdx);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:datafldmarkidx:save")
    public R save(@RequestBody DataFldMarkIdxEntity dataFldMarkIdx) {
        dataFldMarkIdxService.save(dataFldMarkIdx);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:datafldmarkidx:update")
    public R update(@RequestBody DataFldMarkIdxEntity dataFldMarkIdx) {
        dataFldMarkIdxService.updateById(dataFldMarkIdx);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:datafldmarkidx:delete")
    public R delete(@RequestBody Integer[] fldids) {
        dataFldMarkIdxService.removeByIds(Arrays.asList(fldids));

        return R.ok();
    }

}
