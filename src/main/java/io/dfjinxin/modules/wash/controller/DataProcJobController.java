package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.dto.FieldRuleDto;
import io.dfjinxin.modules.wash.service.DataFldWashProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.dfjinxin.modules.wash.entity.DataProcJobEntity;
import io.dfjinxin.modules.wash.service.DataProcJobService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据处理作业
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@RestController
@RequestMapping("wash/dataprocjob")
public class DataProcJobController extends AbstractController {
    @Autowired
    private DataProcJobService dataProcJobService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:dataprocjob:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataProcJobService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/taskList")
    @RequiresPermissions("wash.task.taskList")
    public R taskList(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        PageUtils page = dataProcJobService.queryPublishList(params);
        return R.ok().put("page", page);
    }

    /**
     * 探查任务列表
     * @param params
     * @return
     */
    @RequestMapping("/explorerList")
    @RequiresPermissions("wash.task.taskList")
    public R explorerList(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);   //租户过滤
        params.put("jobType", "0"); //
        PageUtils page = dataProcJobService.queryTaskList(params);
        return R.ok().put("page", page);
    }

    /**
     * 清洗任务列表
     * @param params
     * @return
     */
    @RequestMapping("/washList")
    @RequiresPermissions("wash.task.taskList")
    public R washList(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);   //租户过滤
        params.put("jobType", "1");
        PageUtils page = dataProcJobService.queryTaskList(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{jobid}")
    @RequiresPermissions("wash:dataprocjob:info")
    public R info(@PathVariable("jobid") Integer jobid){
		DataProcJobEntity dataProcJob = dataProcJobService.getById(jobid);

        return R.ok().put("dataProcJob", dataProcJob);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:dataprocjob:save")
    public R save(@RequestBody DataProcJobEntity dataProcJob){
		dataProcJobService.save(dataProcJob);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:dataprocjob:update")
    public R update(@RequestBody DataProcJobEntity dataProcJob){
		dataProcJobService.updateById(dataProcJob);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:dataprocjob:delete")
    public R delete(@RequestBody Integer[] jobids){
		dataProcJobService.removeByIds(Arrays.asList(jobids));
        return R.ok();
    }

    /**
     * 执行数据探查
     * @return
     */
    @RequestMapping("/doExplorer")
    @RequiresPermissions("wash:dataprocjob:doExplorer")
    public R doExplorer(@RequestBody Map<String, Object> params){
        R res = dataProcJobService.doExplorer(params);
        return res;
    }
    /**
     * 执行数据清洗
     * @return
     */
    @RequestMapping("/doWash")
    @RequiresPermissions("wash:dataprocjob:doWash")
    public R doWash(@RequestBody Map<String, Object> params){
        params.put("jobType", "1");
        R res = dataProcJobService.doWash(params);
        return res;
    }

    /**
     * 执行数据清洗
     * @return
     */
    @RequestMapping("/doPublish")
    @RequiresPermissions("wash:dataprocjob:doPublish")
    public R doPublish(@RequestBody Map<String, Object> params){
        params.put("jobType", "1");
        R res = dataProcJobService.doPublish(params);
        return res;
    }

    /**
     * 执行数据清洗
     * @return
     */
    @RequestMapping("/delPublish")
    @RequiresPermissions("wash:dataprocjob:doPublish")
    public R delPublish(@RequestBody Map<String, Object> params){
        params.put("jobType", "1");
        R res = dataProcJobService.delPublish(params);
        return res;
    }
    /**
     * 执行发布任务
     * @return
     */
    @RequestMapping("/doRun")
    public R doRun(@RequestBody Map<String, Object> params){
        params.put("jobType", "4");
        params.put("rate", "100taskList");
        R res = dataProcJobService.doRun(params);
        return res;
    }

    /**
     * 禁用/启用
     * @return
     */
    @RequestMapping("/saveStatus")
    public R saveStatus(@RequestBody DataProcJobEntity dataProcJob){

        return R.ok("操作成功");
    }

    //获取表的规则信息数据
    @RequestMapping("/queryTblRule")
    public R  queryTblRule(@RequestParam Map<String,Object> params ){
        PageUtils list = dataProcJobService.queryTblRule(params);
        return R.ok().put("page",list);
    }
    //保存表的规则信息数据
    @RequestMapping("/saveTblRule")
    public R  saveTblRule(@RequestBody List<FieldRuleDto> list){
        dataProcJobService.saveTblRule(list);
        return R.ok("操作成功");
    }

    @RequestMapping("/queryTaskRule")
    public R  queryTaskRule(@RequestParam Map<String,Object> params ){
        PageUtils list = dataProcJobService.queryTaskRule(params);
        return R.ok().put("page",list);
    }

    //  清洗规则信息
    @RequestMapping("/washRules")
    public R getWashRules(@RequestParam Map<String,Object> params ){
        List<Map<String, Object>> rules= dataProcJobService.getWashRules(params);
        return R.ok().put("rules",rules);
    }
}
