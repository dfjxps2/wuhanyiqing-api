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

import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;
import io.dfjinxin.modules.wash.service.DataFldWashProjService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据字段清洗项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@RestController
@RequestMapping("wash/datafldwashproj")
public class DataFldWashProjController {
    @Autowired
    private DataFldWashProjService dataFldWashProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:datafldwashproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataFldWashProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fldid}")
    @RequiresPermissions("wash:datafldwashproj:info")
    public R info(@PathVariable("fldid") Integer fldid){
		DataFldWashProjEntity dataFldWashProj = dataFldWashProjService.getById(fldid);

        return R.ok().put("dataFldWashProj", dataFldWashProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:datafldwashproj:save")
    public R save(@RequestBody DataFldWashProjEntity dataFldWashProj){
		dataFldWashProjService.save(dataFldWashProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:datafldwashproj:update")
    public R update(@RequestBody DataFldWashProjEntity dataFldWashProj){
		dataFldWashProjService.updateById(dataFldWashProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:datafldwashproj:delete")
    public R delete(@RequestBody Integer[] fldids){
		dataFldWashProjService.removeByIds(Arrays.asList(fldids));

        return R.ok();
    }

}
