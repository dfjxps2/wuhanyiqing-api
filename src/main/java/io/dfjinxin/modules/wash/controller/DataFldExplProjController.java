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

import io.dfjinxin.modules.wash.entity.DataFldExplProjEntity;
import io.dfjinxin.modules.wash.service.DataFldExplProjService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据字段探查项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@RestController
@RequestMapping("wash/datafldexplproj")
public class DataFldExplProjController {
    @Autowired
    private DataFldExplProjService dataFldExplProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:datafldexplproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataFldExplProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fldid}")
    @RequiresPermissions("wash:datafldexplproj:info")
    public R info(@PathVariable("fldid") Integer fldid){
		DataFldExplProjEntity dataFldExplProj = dataFldExplProjService.getById(fldid);

        return R.ok().put("dataFldExplProj", dataFldExplProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:datafldexplproj:save")
    public R save(@RequestBody DataFldExplProjEntity dataFldExplProj){
		dataFldExplProjService.save(dataFldExplProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:datafldexplproj:update")
    public R update(@RequestBody DataFldExplProjEntity dataFldExplProj){
		dataFldExplProjService.updateById(dataFldExplProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:datafldexplproj:delete")
    public R delete(@RequestBody Integer[] fldids){
		dataFldExplProjService.removeByIds(Arrays.asList(fldids));

        return R.ok();
    }

}
