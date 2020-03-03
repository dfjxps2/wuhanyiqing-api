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

import io.dfjinxin.modules.wash.entity.DataTblExplProjEntity;
import io.dfjinxin.modules.wash.service.DataTblExplProjService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据表探查项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@RestController
@RequestMapping("wash/datatblexplproj")
public class DataTblExplProjController {
    @Autowired
    private DataTblExplProjService dataTblExplProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:datatblexplproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataTblExplProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataTblid}")
    @RequiresPermissions("wash:datatblexplproj:info")
    public R info(@PathVariable("dataTblid") Integer dataTblid){
		DataTblExplProjEntity dataTblExplProj = dataTblExplProjService.getById(dataTblid);

        return R.ok().put("dataTblExplProj", dataTblExplProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:datatblexplproj:save")
    public R save(@RequestBody DataTblExplProjEntity dataTblExplProj){
		dataTblExplProjService.save(dataTblExplProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:datatblexplproj:update")
    public R update(@RequestBody DataTblExplProjEntity dataTblExplProj){
		dataTblExplProjService.updateById(dataTblExplProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:datatblexplproj:delete")
    public R delete(@RequestBody Integer[] dataTblids){
		dataTblExplProjService.removeByIds(Arrays.asList(dataTblids));

        return R.ok();
    }

}
