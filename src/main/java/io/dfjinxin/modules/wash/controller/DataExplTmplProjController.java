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

import io.dfjinxin.modules.wash.entity.DataExplTmplProjEntity;
import io.dfjinxin.modules.wash.service.DataExplTmplProjService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据探查模板项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@RestController
@RequestMapping("wash/dataexpltmplproj")
public class DataExplTmplProjController {
    @Autowired
    private DataExplTmplProjService dataExplTmplProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:dataexpltmplproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataExplTmplProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataExplTmplid}")
    @RequiresPermissions("wash:dataexpltmplproj:info")
    public R info(@PathVariable("dataExplTmplid") Integer dataExplTmplid){
		DataExplTmplProjEntity dataExplTmplProj = dataExplTmplProjService.getById(dataExplTmplid);

        return R.ok().put("dataExplTmplProj", dataExplTmplProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:dataexpltmplproj:save")
    public R save(@RequestBody DataExplTmplProjEntity dataExplTmplProj){
		dataExplTmplProjService.save(dataExplTmplProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:dataexpltmplproj:update")
    public R update(@RequestBody DataExplTmplProjEntity dataExplTmplProj){
		dataExplTmplProjService.updateById(dataExplTmplProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:dataexpltmplproj:delete")
    public R delete(@RequestBody Integer[] dataExplTmplids){
		dataExplTmplProjService.removeByIds(Arrays.asList(dataExplTmplids));

        return R.ok();
    }

}
