package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;
import io.dfjinxin.modules.wash.service.PrdDataProcJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 已发布数据处理作业
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@RestController
@RequestMapping("sys/prddataprocjob")
public class PrdDataProcJobController {
    @Autowired
    private PrdDataProcJobService prdDataProcJobService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:prddataprocjob:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = prdDataProcJobService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{jobid}")
    @RequiresPermissions("sys:prddataprocjob:info")
    public R info(@PathVariable("jobid") Integer jobid){
		PrdDataProcJobEntity prdDataProcJob = prdDataProcJobService.getById(jobid);

        return R.ok().put("prdDataProcJob", prdDataProcJob);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:prddataprocjob:save")
    public R save(@RequestBody PrdDataProcJobEntity prdDataProcJob){
		prdDataProcJobService.save(prdDataProcJob);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:prddataprocjob:update")
    public R update(@RequestBody PrdDataProcJobEntity prdDataProcJob){
		prdDataProcJobService.updateById(prdDataProcJob);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:prddataprocjob:delete")
    public R delete(@RequestBody Integer[] jobids){
		prdDataProcJobService.removeByIds(Arrays.asList(jobids));

        return R.ok();
    }

}
