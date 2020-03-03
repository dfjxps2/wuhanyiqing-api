package io.dfjinxin.modules.wash.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.wash.entity.FldExplResultEntity;
import io.dfjinxin.modules.wash.service.FldExplResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 字段探查结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@RestController
@RequestMapping("wash/fldexplresult")
public class FldExplResultController {
    @Autowired
    private FldExplResultService fldExplResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:fldexplresult:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fldExplResultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{jobid}")
    @RequiresPermissions("wash:fldexplresult:info")
    public R info(@PathVariable("jobid") Integer jobid){
		FldExplResultEntity fldExplResult = fldExplResultService.getById(jobid);

        return R.ok().put("fldExplResult", fldExplResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:fldexplresult:save")
    public R save(@RequestBody FldExplResultEntity fldExplResult){
		fldExplResultService.save(fldExplResult);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:fldexplresult:update")
    public R update(@RequestBody FldExplResultEntity fldExplResult){
		fldExplResultService.updateById(fldExplResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:fldexplresult:delete")
    public R delete(@RequestBody Integer[] jobids){
		fldExplResultService.removeByIds(Arrays.asList(jobids));

        return R.ok();
    }

    /**
     * 探查报告
     */
    @RequestMapping("/report")
    @RequiresPermissions("wash:fldexplresult:report")
    public R expReport(@RequestParam Map<String, Object> params){
        PageUtils expReport = fldExplResultService.expReport(params);
        List<Map<String,Object>> expInfo = fldExplResultService.expInfo(params);
        List<Map<String,Object>> expProj = fldExplResultService.expProj(params);
        R d = R.ok();
        d.put("page", expReport);
        d.put("expInfo", expInfo);
        d.put("expProj", expProj);
        return d;
    }

    /**
     * 导出探查报告
     */
    @RequestMapping("/download")
    @RequiresPermissions("wash:fldexplresult:download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        fldExplResultService.download(request,response);
    }


}
