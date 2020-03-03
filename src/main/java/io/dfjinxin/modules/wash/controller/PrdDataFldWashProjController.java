package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.wash.entity.PrdDataFldWashProjEntity;
import io.dfjinxin.modules.wash.service.PrdDataFldWashProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 已发布数据字段清洗项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-27 14:38:06
 */
@RestController
@RequestMapping("sys/prddatafldwashproj")
public class PrdDataFldWashProjController {
    @Autowired
    private PrdDataFldWashProjService prdDataFldWashProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:prddatafldwashproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = prdDataFldWashProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{fldid}")
    @RequiresPermissions("sys:prddatafldwashproj:info")
    public R info(@PathVariable("fldid") Integer fldid){
		PrdDataFldWashProjEntity prdDataFldWashProj = prdDataFldWashProjService.getById(fldid);

        return R.ok().put("prdDataFldWashProj", prdDataFldWashProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:prddatafldwashproj:save")
    public R save(@RequestBody PrdDataFldWashProjEntity prdDataFldWashProj){
		prdDataFldWashProjService.save(prdDataFldWashProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:prddatafldwashproj:update")
    public R update(@RequestBody PrdDataFldWashProjEntity prdDataFldWashProj){
		prdDataFldWashProjService.updateById(prdDataFldWashProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:prddatafldwashproj:delete")
    public R delete(@RequestBody Integer[] fldids){
		prdDataFldWashProjService.removeByIds(Arrays.asList(fldids));

        return R.ok();
    }

}
