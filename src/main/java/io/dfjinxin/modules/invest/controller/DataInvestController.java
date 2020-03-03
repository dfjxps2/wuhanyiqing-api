package io.dfjinxin.modules.invest.controller;

import com.alibaba.fastjson.JSONObject;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.invest.entity.DataInvestEntity;
import io.dfjinxin.modules.invest.entity.TableFldEntity;
import io.dfjinxin.modules.invest.service.DataInvestService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.entity.DataProcJobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by GaoPh on 2019/6/18.
 */
@RestController
@RequestMapping("/invest")
public class DataInvestController extends AbstractController{
    @Autowired
   private DataInvestService dataInvestService;

   @GetMapping("/list")
   public R  investList(@RequestParam Map<String,Object> params){
       appendAuthFilter(params);
       PageUtils page = dataInvestService.queryInvestList(params);
       return R.ok().put("page",page);
   }

   //获取表字段信息
    @GetMapping("/fldlist")
    public R  fieldList(@RequestParam Map<String,Object> params){
        PageUtils flds = dataInvestService.queryTblFld(params);
        return R.ok().put("fld",flds);
    }


   @GetMapping("/dataResouce")
   public R resList(){
       List<Map<String, Object>> src= dataInvestService.queryResources();
    return R.ok().put("srcs",src);
   }

   //获取表数据库信息
   @GetMapping("/dbResouce")
   public R dbList(){
       Map<String, Object> map = new HashMap<>();
       appendAuthFilter(map);
       List<Map<String, Object>> res = new ArrayList<>();
       List<Map<String, Object>> src= dataInvestService.queryDb(map);
//       map.clear();
//       map.put("dbid","all");
//       map.put("dbCnNm","全部");
//       res.add(map);
//       for(Map<String, Object> data : src){
//           if(data.get("dbCnNm").equals("临时库")|| data.get("dbCnNm").equals("问题库") ){
//               continue;
//           }
//           res.add(data);
//       }
       return R.ok().put("srcs",src);
   }
     //获取表字段下拉框的值
    @GetMapping("/flds")
    public R  fldList(@RequestParam Map<String,Object> params){
        List<Map<String, Object>> flds = dataInvestService.getTblFlds(params);
        return R.ok().put("flds",flds);
    }

    //字段信息页的保存
    @PostMapping("/saveFlds")
    public R saveFlds(@RequestBody List<Map<String,Object>> list ){
        dataInvestService.saveFldTmpls(list);
        return R.ok();
    }

    //表信息页的保存
    @PostMapping("/saveTbl")
    public R saveTbls(@RequestBody List<DataInvestEntity> list){
        Iterator<DataInvestEntity> it = list.iterator();
        while (it.hasNext()){
            DataInvestEntity dt = it.next();
             dataInvestService.saveTables(dt);

        }


        return R.ok();
    }

    //表信息批量设置
    @PostMapping("/batchSaveTbl")
    public R batchSaveTbls(@RequestBody JSONObject tbls){
        List<String> ts = (List<String>) tbls.get("tbls");
        Integer srcId = (Integer) tbls.get("dataSrcId");
        String icf = (String) tbls.get("incrOrFull");
        dataInvestService.batchSaveTables(ts,srcId,icf);
        return R.ok();
    }

//    字段信息批处理
    @PostMapping("/batchSaveFld")
    public R batchSaveFlds( @RequestBody JSONObject flds){
        Integer key = null;
        Integer fn =null;
        List<Integer> ts = (List<Integer>) flds.get("fldIds");
        List<Integer> det = (List<Integer>) flds.get("dataExplTmplid");
        if(flds.get("fldKey")!="" &&flds.get("fldKey")!=null ){
            key = (Integer) flds.get("fldKey");
        }
        if(flds.get("fldNull")!="" &&flds.get("fldNull")!=null){
            fn  = (Integer) flds.get("fldNull");
        }
        dataInvestService.batchSaveFlds(ts,key,fn,det);
        return R.ok();
    }
//  字段模板信息
    @GetMapping("/tmpl")
    public R getExplTmpl(@RequestParam Map<String,Object> params ){
        appendAuthFilter(params);
        List<Map<String, Object>> tmpls= dataInvestService.getExplTempls(params);
        return R.ok().put("tmpls",tmpls);
    }

    //自动匹配探查模板
    @PostMapping("/markFilds")
    public R fieldAutoMark(@RequestBody List<Integer> tableIds){
        Map<String, Object> params = new HashMap<>();
        appendAuthFilter(params);
        R res = dataInvestService.fieldAutoMark(tableIds,params);
        return res;
    }

    //元数据更新
    @PostMapping("/metadataUpdate")
    public R metadataUpdate(){
      Map<String, Object> params = new HashMap<>();
      appendAuthFilter(params);
      R r=  dataInvestService.execUpdate(params);
      return r;
    }

    //获取元数据更新状态
    @PostMapping("/jobStatus")
    public R getJobStatus(){
        Map<String, Object> res=  dataInvestService.getJobStatus();
        if(res == null){
            res = new HashMap<>();
            res.put("jobStat","FIRST");
        }
        return R.ok().put("job",res);
    }


    //获取表的规则信息数据
    @GetMapping("/ruleList")
    public R  fielRuledList(@RequestParam Map<String,Object> params ){
        List<Map<String, Object>> rules = dataInvestService.getTblRule(params);
        return R.ok().put("rules",rules);
    }

    //  清洗规则信息
    @GetMapping("/washRules")
    public R getWashRules(@RequestParam Map<String,Object> params ){
        List<Map<String, Object>> rules= dataInvestService.getWashRules();
        return R.ok().put("rules",rules);
    }


    //    清洗规则表批处理
    @PostMapping("/batchSaveRules")
    public R batchSaveRule( @RequestBody JSONObject params){
        List<String> ts = (List<String>) params.get("fldIds");
        Integer key = (Integer) params.get("fldKey");
        Integer fn = (Integer) params.get("fldNull");
        Integer det = (Integer) params.get("dataExplTmplid");
        List<String> rs = (List<String>) params.get("rules");
        List<Map<String,Object>> rf= dataInvestService.getFldRules(ts);
        return R.ok();
    }


}
