package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobResultEntity;
import io.dfjinxin.modules.wash.service.PrdDataProcJobResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 已发布作业表级执行结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@RestController
@RequestMapping("sys/prddataprocjobresult")
public class PrdDataProcJobResultController {
    @Autowired
    private PrdDataProcJobResultService prdDataProcJobResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:prddataprocjobresult:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = prdDataProcJobResultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{jobid}")
    @RequiresPermissions("sys:prddataprocjobresult:info")
    public R info(@PathVariable("jobid") Integer jobid){
		PrdDataProcJobResultEntity prdDataProcJobResult = prdDataProcJobResultService.getById(jobid);

        return R.ok().put("prdDataProcJobResult", prdDataProcJobResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:prddataprocjobresult:save")
    public R save(@RequestBody PrdDataProcJobResultEntity prdDataProcJobResult){
		prdDataProcJobResultService.save(prdDataProcJobResult);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:prddataprocjobresult:update")
    public R update(@RequestBody PrdDataProcJobResultEntity prdDataProcJobResult){
		prdDataProcJobResultService.updateById(prdDataProcJobResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:prddataprocjobresult:delete")
    public R delete(@RequestBody Integer[] jobids){
		prdDataProcJobResultService.removeByIds(Arrays.asList(jobids));

        return R.ok();
    }
}
