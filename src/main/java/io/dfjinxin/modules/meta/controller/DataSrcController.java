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

import io.dfjinxin.modules.meta.entity.DataSrcEntity;
import io.dfjinxin.modules.meta.service.DataSrcService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据源
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@RestController
@RequestMapping("meta/datasrc")
public class DataSrcController {
    @Autowired
    private DataSrcService dataSrcService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataSrcService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/listByTnmt")
    public R listByTnmt(@RequestParam Map<String, Object> params){
        PageUtils page = dataSrcService.listByTnmt(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataSrcid}")
    @RequiresPermissions("meta:datasrc:info")
    public R info(@PathVariable("dataSrcid") Integer dataSrcid){
		DataSrcEntity dataSrc = dataSrcService.getById(dataSrcid);

        return R.ok().put("dataSrc", dataSrc);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:datasrc:save")
    public R save(@RequestBody DataSrcEntity dataSrc){
		dataSrcService.save(dataSrc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:datasrc:update")
    public R update(@RequestBody DataSrcEntity dataSrc){
		dataSrcService.updateById(dataSrc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:datasrc:delete")
    public R delete(@RequestBody Integer[] dataSrcids){
		dataSrcService.removeByIds(Arrays.asList(dataSrcids));

        return R.ok();
    }

}
