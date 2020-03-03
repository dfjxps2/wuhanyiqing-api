package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.entity.PrdDataWashFldResultEntity;
import io.dfjinxin.modules.wash.service.PrdDataWashFldResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 已发布作业字段清洗结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@RestController
@RequestMapping("sys/prddatawashfldresult")
public class PrdDataWashFldResultController  extends AbstractController {
    @Autowired
    private PrdDataWashFldResultService prdDataWashFldResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:prddatawashfldresult:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = prdDataWashFldResultService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{jobid}")
    @RequiresPermissions("sys:prddatawashfldresult:info")
    public R info(@PathVariable("jobid") Integer jobid){
		PrdDataWashFldResultEntity prdDataWashFldResult = prdDataWashFldResultService.getById(jobid);

        return R.ok().put("prdDataWashFldResult", prdDataWashFldResult);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:prddatawashfldresult:save")
    public R save(@RequestBody PrdDataWashFldResultEntity prdDataWashFldResult){
		prdDataWashFldResultService.save(prdDataWashFldResult);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:prddatawashfldresult:update")
    public R update(@RequestBody PrdDataWashFldResultEntity prdDataWashFldResult){
		prdDataWashFldResultService.updateById(prdDataWashFldResult);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:prddatawashfldresult:delete")
    public R delete(@RequestBody Integer[] jobids){
		prdDataWashFldResultService.removeByIds(Arrays.asList(jobids));

        return R.ok();
    }

    /**
     * 清洗报告
     */
    @RequestMapping("/report")
    @RequiresPermissions("sys:prddatawashfldresult:report")
    public R washReport(@RequestParam Map<String, Object> params){
        params.put("tnmtid", getTenantId());
        appendLastExecDate(params);
        PageUtils washReport = prdDataWashFldResultService.washReport(params);
        List<Map<String,Object>> washInfo = prdDataWashFldResultService.washInfo(params);
        List<Map<String,Object>> washProj = prdDataWashFldResultService.washProj(params);
        R d = R.ok();
        d.put("page", washReport);
        d.put("washInfo", washInfo);
        d.put("washProj", washProj);
        return d;
    }

    /**
     * 导出探查报告
     */
    @RequestMapping("/download")
    @RequiresPermissions("sys:prddatawashfldresult:download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        prdDataWashFldResultService.download(request,response);
    }

    /**
     * 查询数据来源列表
     */
    @RequestMapping("/dataSrcList")
    @RequiresPermissions("sys:prddatawashfldresult:dataSrcList")
    public R dataSrcList(@RequestParam Map<String, Object> params){
        params.put("tnmtid", getTenantId());
        List<Map<String,Object>> srcList = prdDataWashFldResultService.dataSrcList(params);
        return R.ok().put("srcList", srcList);
    }

    /**
     * 列表
     */
    @RequestMapping("/tableFld")
    @RequiresPermissions("sys:prddatawashfldresult:tableFld")
    public R tableFld(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        appendLastExecDate(params);
        params.put("tnmtid", getTenantId());
        R d = prdDataWashFldResultService.tableFldData(params);
        return d;
    }

    /**
     * 清洗前后数据对比
     * @param params
     * @return
     */
    @RequestMapping("/comparisonData")
    @RequiresPermissions("sys:prddatawashfldresult:comparisonData")
    public R comparisonData(@RequestParam Map<String, Object> params){
        appendLastExecDate(params);
        params.put("tnmtid", getTenantId());
        R d = prdDataWashFldResultService.comparisonData(params);
        return d;
    }
    /**
     * 总表清洗报告
     */
    @RequestMapping("/tableReport")
    @RequiresPermissions("sys:prddatawashfldresult:tableReport")
    public R tableReport(@RequestParam Map<String, Object> params){
        params.put("tnmtid", getTenantId());
        PageUtils washReport = prdDataWashFldResultService.tableReport(params);
        R d = R.ok();
        d.put("page", washReport);
        return d;
    }


    /**
     * 表问题分布
     */
    @RequestMapping("/fieldProInfo")
    @RequiresPermissions("sys:prddatawashfldresult:fieldProInfo")
    public R fieldProInfo(@RequestParam Map<String, Object> params){
        appendLastExecDate(params);
        params.put("tnmtid", getTenantId());
        PageUtils field = prdDataWashFldResultService.fieldProInfo(params);
        R d = R.ok();
        d.put("page", field);
        return d;
    }

    /**
     * 总表清洗报告表报告
     */
    @RequestMapping("/tableReportInfo")
    @RequiresPermissions("sys:prddatawashfldresult:tableReportInfo")
    public R tableReportInfo(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        appendLastExecDate(params);
        PageUtils washReport = prdDataWashFldResultService.tableReportInfo(params);
        R d = R.ok();
        d.put("page", washReport);
        return d;
    }
    /**
     * 总表清洗报告表报告
     */
    @RequestMapping("/tableProInfo")
    @RequiresPermissions("sys:prddatawashfldresult:tableProInfo")
    public R tableProInfo(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        appendLastExecDate(params);
        PageUtils washReport = prdDataWashFldResultService.tableProInfo(params);
        R d = R.ok();
        d.put("page", washReport);
        return d;
    }

    /**
     * 是否查询最新日期清洗结果
     * @param params
     */
    private void appendLastExecDate(Map<String, Object> params){
        //id <- map(srcId)
        if(!params.containsKey("id")){
            if(params.containsKey("srcId")){
                params.put("id", params.get("srcId"));
            }
        }

        Object lastDate = params.get("date");
        if(lastDate == null || "".equals(lastDate.toString())){
            params.put("date",prdDataWashFldResultService.lastExecDate(params));
        }
    }

    /**
     * 查询问题对比数据
     * @param params
     * @return
     */
    @RequestMapping("/tableProData")
    @RequiresPermissions("sys:prddatawashfldresult:tableProData")
    public R tableProData(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        appendLastExecDate(params);
        PageUtils washReport = prdDataWashFldResultService.tableProInfo(params);
        R d = R.ok();
        d.put("page", washReport);
        return d;
    }

    @RequestMapping("/lastExecDate")
    @RequiresPermissions("sys:prddatawashfldresult:lastExecDate")
    public R lastExecDate(@RequestParam Map<String, Object> params){
        appendLastExecDate(params);
        R d = R.ok();
        d.put("date", params.get("date"));
        return d;
    }
}
