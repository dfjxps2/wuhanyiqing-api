package io.dfjinxin.modules.storage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import io.dfjinxin.modules.meta.service.DataFldService;
import io.dfjinxin.modules.meta.service.DbService;
import io.dfjinxin.modules.storage.dto.*;
import io.dfjinxin.modules.storage.service.DesenService;
import io.dfjinxin.modules.storage.utils.StorageUtils;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobResultEntity;
import io.dfjinxin.modules.wash.entity.PrdDataWashFldResultEntity;
import io.dfjinxin.modules.wash.service.PrdDataProcJobResultService;
import io.dfjinxin.modules.wash.service.PrdDataProcJobService;
import io.dfjinxin.modules.wash.service.PrdDataWashFldResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/storage/desen")
public class DesenController extends AbstractController {

    @Autowired
    private DesenService desenService;
    @Autowired
    private DataFldService dataFldService;
    @Autowired
    private PrdDataProcJobResultService prdDataProcJobResultService;
    @Autowired
    private PrdDataWashFldResultService prdDataWashFldResultService;
    @Autowired
    private DbService dbService;

    @PostMapping("/query")
    public R query(@RequestBody DesenForm desenForm){
        desenForm.markToInt();
        PageUtils page = desenService.query(desenForm, this.getTenantId());
        return R.ok().put("page", page);
    }

    @PostMapping("/dataTblid")
    public R dataTblid(Integer tbId){
       return desenService.queryFldByTbId(tbId);
    }

    @PostMapping("/queryHistory")
    public R queryHistory(Integer tbId){
        R r = R.ok();

        List<PrdDataProcJobEntity> list = desenService.queryHistory(tbId);
        r.put("list", list);

//        if(null != list && list.size() > 0){
//            QueryWrapper queryWrapper = new QueryWrapper<PrdDataProcJobResultEntity>();
//            queryWrapper.eq("Jobid", list.get(0).getJobid());
//            List<PrdDataProcJobResultEntity> proResults = prdDataProcJobResultService.list(queryWrapper);
//            r.put("proResults", proResults);
//        }

        return r;
    }

    @PostMapping("/queryDataByJobid")
    public R queryDataByJobid(Integer jobid){
        List<PrdDataWashFldResultEntity> list = prdDataWashFldResultService.queryDataByJobid(jobid);
        return R.ok().put("list", list);
    }

    @PostMapping("/save")
    public R save(@RequestBody SaveDesen saveDesen){
        desenService.saveDataFldWashProj(saveDesen);
        return R.ok();
    }

    @PostMapping("/setting")
    public R setting(@RequestBody SaveDesen saveDesen){
        String czType = desenService.setting(saveDesen);
        return R.ok().put("czType", czType);
    }

    @GetMapping("/queryDataPartType")
    public R queryDataPartType(){
        List<PartDto> list = desenService.queryDataPartType(this.getTenantId().intValue());
        return R.ok().put("data", list);
    }

    @GetMapping("/queryDbs")
    public R queryDbs(Integer Partid){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Partid", Partid);
        return R.ok().put("list", desenService.queryDbsRestrict(map));
    }
 }
