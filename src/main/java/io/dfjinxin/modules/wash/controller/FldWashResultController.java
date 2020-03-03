package io.dfjinxin.modules.wash.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.utils.UserContenUtils;
import io.dfjinxin.modules.wash.entity.FldWashResultEntity;
import io.dfjinxin.modules.wash.service.FldWashResultService;
import org.springframework.beans.factory.annotation.Autowired;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 字段清洗结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@RestController
@RequestMapping("wash/fldwashresult")
public class FldWashResultController extends AbstractController{
    @Autowired
    private FldWashResultService fldWashResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:fldwashresult:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fldWashResultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{jobid}")
    @RequiresPermissions("wash:fldwashresult:info")
    public R info(@PathVariable("jobid") Integer jobid){
		FldWashResultEntity fldWashResult = fldWashResultService.getById(jobid);

        return R.ok().put("fldWashResult", fldWashResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:fldwashresult:save")
    public R save(@RequestBody FldWashResultEntity fldWashResult){
		fldWashResultService.save(fldWashResult);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:fldwashresult:update")
    public R update(@RequestBody FldWashResultEntity fldWashResult){
		fldWashResultService.updateById(fldWashResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:fldwashresult:delete")
    public R delete(@RequestBody Integer[] jobids){
		fldWashResultService.removeByIds(Arrays.asList(jobids));

        return R.ok();
    }

    /**
     * 清洗报告
     */
    @RequestMapping("/report")
    @RequiresPermissions("wash:fldwashresult:report")
    public R washReport(@RequestParam Map<String, Object> params){
        PageUtils washReport = fldWashResultService.washReport(params);
        List<Map<String,Object>> washInfo = fldWashResultService.washInfo(params);
        List<Map<String,Object>> washProj = fldWashResultService.washProj(params);
        R d = R.ok();
        d.put("page", washReport);
        d.put("washInfo", washInfo);
        d.put("washProj", washProj);
        return d;
    }

    /**
     * 导出试清洗报告
     */
    @RequestMapping("/download")
    @RequiresPermissions("wash:fldwashresult:download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        fldWashResultService.download(request,response);
    }

    /**
     * 导出试清洗问题明细
     */
    @RequestMapping("/downloadDetail")
    @RequiresPermissions("wash:fldwashresult:download")
    public void downloadDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("Tnmtid", getTenantId());
        fldWashResultService.downloadDetail(request,response);
    }
}
