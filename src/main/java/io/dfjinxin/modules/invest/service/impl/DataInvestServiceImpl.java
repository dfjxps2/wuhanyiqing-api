package io.dfjinxin.modules.invest.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.config.MetadataUpdateParams;
import io.dfjinxin.modules.invest.dao.DataInvestDao;
import io.dfjinxin.modules.invest.entity.DataInvestEntity;
import io.dfjinxin.modules.invest.service.DataInvestService;
import io.dfjinxin.modules.wash.entity.DataProcJobEntity;
import io.dfjinxin.modules.wash.service.DataProcJobService;
import io.dfjinxin.modules.wash.service.impl.DataProcJobServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by GaoPh on 2019/6/18.
 */
@Service("dataInvestService")
public class DataInvestServiceImpl extends ServiceImpl<DataInvestDao, DataInvestEntity> implements DataInvestService {
    private static final String JOB_STA_RUN = "RUNNING";
    @Autowired
    private MetadataUpdateParams metadataUpdateParams;
    @Autowired
    private DataProcJobService dataProcJobService;
    @Override
    public PageUtils queryInvestList(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<DataInvestEntity> page = baseMapper.queryInvestList(new Page(no, limit), params);
        for(DataInvestEntity die:page.getRecords()) {
            if(die.getLoadFreq() != null){
            switch ((Integer)die.getLoadFreq()) {
                case 0:
                    die.setLoadFreqStr("每日接入");
                    break;
                case 1:
                    die.setLoadFreqStr("每周接入");
                    break;
                case 2:
                    die.setLoadFreqStr("每月接入");
                    break;
                case 3:
                    die.setLoadFreqStr("每季接入");
                    break;
                case 4:
                    die.setLoadFreqStr("每半年接入");
                    break;
                case 5:
                    die.setLoadFreqStr("每年接入");
                    break;
                case 6:
                    die.setLoadFreqStr("一次性接入");
                    break;
                case 7:
                    die.setLoadFreqStr("不定期接入");
                    break;
            }
            }else {
                die.setLoadFreqStr("未指定");
            }
        }
        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> queryResources() {
        List<Map<String, Object>> srcs = baseMapper.queryResources();
        return srcs;
    }


    @Override
    public PageUtils queryTblFld(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<Map<String, Object>> page = baseMapper.queryTblFld(new Page(no, limit), params);
        List<Map<String, Object>> mouldList = page.getRecords();
        List<Map<String, Object>> tmList = baseMapper.queryTempl();
        for (Map<String, Object> mu : mouldList) {
            List<Integer> list = new LinkedList<Integer>();
            for (Map<String, Object> pr : tmList) {
                if (mu.get("fldId").toString().equals(pr.get("fld").toString())) {
                    list.add((Integer) pr.get("dataExplTmplid"));
                }
            }
            mu.put("tmpList", list);
        }
        page.setRecords(mouldList);
        return new PageUtils(page);
    }

    @Override
    public List<Map<String, Object>> getTblFlds(Map<String, Object> tps) {
        List<Map<String, Object>> fds = baseMapper.getTblFlds(tps);
        return fds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFldTmpls(List<Map<String, Object>> list) {
        List<Map<String, Object>> newTmp = new ArrayList<>();
        Map<String, Object> tps = null;
        for (Map<String, Object> map : list) {
            baseMapper.saveFlds(map);
            Integer fid = (Integer) map.get("fldId");
            List<Integer> tmp = (List<Integer>) map.get("tmpList");
            if ( fid != null) {
                saveFldWashProj(fid, tmp);
            }
            if (fid != null) {
                baseMapper.deleFldsTmpl(fid);
            }
            if (tmp.size() != 0) {
                for (Integer data : tmp) {
                    tps = new HashMap<>();
                    tps.put("fid", fid);
                    tps.put("tmplId", data);
                    newTmp.add(tps);
                }
            }
        }
        if (newTmp.size() > 0) {
            baseMapper.insertFldTmpl(newTmp);
        }
    }

    @Override
    public List<Map<String, Object>> getFldRules(List<String> list) {
        List<Map<String, Object>> rs = baseMapper.getFldRules(list);
        return rs;
    }


    //字段保存功能
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchSaveFlds(List<Integer> list, Integer key, Integer fn, List<Integer> tmpl) {
        baseMapper.saveTableFlds(list, key, fn);
        List<Map<String, Object>> fldTmpl = new ArrayList<>();
        Map<String, Object> map = null;
        for (Integer fid : list) {
            if (fid!=null) {
                saveFldWashProj(fid, tmpl);
            }
            for (Integer tmplId : tmpl) {
                map = new HashMap<>();
                map.put("fid", fid);
                map.put("tmplId", tmplId);
                map.put("projType", 1);
                fldTmpl.add(map);
            }
        }

        for (Integer fid : list) {
            baseMapper.deleFldsTmpl(fid);
        }
        if (fldTmpl.size() > 0) {
            baseMapper.insertFldTmpl(fldTmpl);
        }
    }


    @Override
    public void saveTables(DataInvestEntity dit) {
        baseMapper.saveTables(dit);
    }

    @Override
    public void batchSaveTables(List<String> list, Integer srcid, String icf) {
        baseMapper.batchSaveTables(list, srcid, icf);
    }


    @Override
    public List<Map<String, Object>> getExplTempls(Map<String, Object> tem) {
        List<Map<String, Object>> tmpls = baseMapper.getExplTempls(tem);
        return tmpls;
    }

    @Override
    public List<Map<String, Object>> getTblRule(Map<String, Object> tbl) {
        List<Map<String, Object>> tblRules = baseMapper.getTblRule(tbl);
        return tblRules;
    }

    @Override
    public List<Map<String, Object>> getWashRules() {
        List<Map<String, Object>> rules = baseMapper.getWashRules();
        return rules;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R fieldAutoMark(List<Integer> tableIds,Map<String,Object> tenantId) {
        //得到要更新的表字段
        List<Map<String, Object>> markDataList = baseMapper.getFieldByIds(tableIds);
        //得到 字段匹配规则
        List<Map<String, Object>> markRules = baseMapper.getMarkRules(tenantId);

        //存储最新结果
        List<Map<String, Object>> fldTmp = new ArrayList<>();

        //进行匹配
        for (Map<String, Object> rl : markRules) {
            Integer mathStgy = (Integer) rl.get("Obj_Match_Stgy");
            Integer tmpId = (Integer) rl.get("Data_Expl_Tmplid");
            String ruleExprs = (String) rl.get("Rule_Exprs");
            if (Strings.isNullOrEmpty(ruleExprs)) {
                continue;
            }
            Pattern pattern = null;

            try{
                pattern = Pattern.compile(ruleExprs);

                if (mathStgy == 0) {//正则
                    markfldMatch(markDataList, tmpId, fldTmp, pattern, true);
                } else if (mathStgy == 1) {//模糊匹配
                    markfldMatch(markDataList, tmpId, fldTmp, pattern, false);
                } else {
                    continue;
                }
            }catch(PatternSyntaxException e){
                return R.error("正则表达式错误："+ruleExprs);
            }

        }

        for (Map<String, Object> fd : markDataList) {
            Integer fid = (Integer) fd.get("fldId");
            baseMapper.deleFldsTmpl(fid);
        }

        if (fldTmp.size() > 0) {
            baseMapper.insertFldTmpl(fldTmp);
        }
        return R.ok();
    }


    @Override
    public R execUpdate(Map<String, Object> param) {
        R r = null;
        Map<String, Object> map = new HashMap<>();
        try {
            String targetScript = metadataUpdateParams.getRunScript();
            Integer jobType = Integer.parseInt(metadataUpdateParams.getJobType());
            String tenantId = String.valueOf(param.get("tenantId"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, 1);
            Date st = calendar.getTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(new Date().getTime());
            Date jobDate = null;
            try {
                jobDate = df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            DataProcJobEntity entity = new DataProcJobEntity();
            entity.setJobStus(JOB_STA_RUN);
            entity.setJobType(jobType);
            entity.setStartTm(st);
            entity.setRfrshTm(st);
            entity.setDataStartDt(jobDate);
            entity.setDataTerminateDt(jobDate);
            boolean flg= dataProcJobService.save(entity);
            if(!flg){
                return R.error().put("msg","更新作业创建失败!");
            }
            r = R.ok().put("job", entity);
            DataProcJobEntity job = (DataProcJobEntity) r.get("job");
            String jobId = String.valueOf(job.getJobid());
            map.put("cmd", targetScript);
            map.put("tenantId", tenantId);
            map.put("jobId", jobId);
        }catch (Exception e){
              return R.error("未知错误，更新失败！！！");
        }
        return   execShell(map);
    }

    @Override
    public Map<String, Object> getJobStatus() {
        Map<String, Object> res = baseMapper.getJobStatus();
        return res;
    }

    //获取租户可查看数据库信息
    @Override
    public List<Map<String, Object>> queryDb(Map<String, Object> map) {
        return baseMapper.getDbDatas(map);
    }

    private void markfldMatch(List<Map<String, Object>> markDataList,
                              Integer tmplId,
                              List<Map<String, Object>> resList,
                              Pattern pattern,
                              boolean isFullMatch) {
        Map<String, Object> map = null;
        for (Map<String, Object> fld : markDataList) {
            String cnName = null;
            String enName = null;
            Integer markFldId = null;
            map = new HashMap<>();

            if (fld != null) {
                cnName = mstr(fld, "fldCnNm");
                enName = mstr(fld, "fldPhysNm");
                markFldId = (Integer) fld.get("fldId");
            } else {
                continue;
            }

            Matcher cnMatcher = pattern.matcher(cnName);
            Matcher enMatcher = pattern.matcher(enName);
            if (isFullMatch) {
                if (cnMatcher.matches() || enMatcher.matches()) {//命中.打
                    map.put("fid", markFldId);
                    map.put("tmplId", tmplId);
                    map.put("projType", 0);
                    resList.add(map);
                }
            } else {
                if (cnMatcher.find() || enMatcher.find()) {//命中.打
                    map.put("fid", markFldId);
                    map.put("tmplId", tmplId);
                    map.put("projType", 0);
                    resList.add(map);
                }
            }
        }
    }
    private String mstr(Map<String, Object> map, String key){
        if(!map.containsKey(key)){
            return "";
        }
        Object obj = map.get(key);
        return obj == null ? "" : obj.toString();
    }
    //将字段对应的清洗规则保存到数据字段清洗项目表

    private void saveFldWashProj(Integer fid, List<Integer> tid) {
        List<Map<String, Object>> fldWash = new ArrayList<>();
        if(tid.size()>0) {
            List<Integer> rds = new ArrayList<>();
            for (Integer fld : tid) {
                List<Integer> rid = baseMapper.getCmpuIds(fld);
                for (Integer da : rid) {
                    if (da != null) {
                        rds.add(da);
                    } else {
                        continue;
                    }
                }
            }
            List<Integer> rids = removeDuplicate(rds);

            Map<String, Object> map = null;
            if (rids.size() > 0) {
                for (int i = 0; i < rids.size(); i++) {
                    map = new HashMap<>();
                    map.put("fid", fid);
                    map.put("dataWashCm", rids.get(i));
                    map.put("exctOrd", i + 1);
                    fldWash.add(map);
                }
            }
        }
        baseMapper.deleteFldCumId(fid);
        if (fldWash.size() > 0) {
            baseMapper.insertFldWashProj(fldWash);
        }
    }


    //list去重
    public List<Integer> removeDuplicate(List<Integer> list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    //执行本地shell 脚本
    public R execShell(Map<String,Object> map){
        String script = (String) map.get("cmd");
        String  tenId = String.valueOf(map.get("tenantId")) ;
        String jobId = (String) map.get("jobId");
        String[] shell = {script,jobId,tenId};
        try {
          Process proc = Runtime.getRuntime().exec(shell);
          //读取标准输出流
         BufferedReader bf=   new BufferedReader(new InputStreamReader(proc.getInputStream()));
         String line ;
         while ((line=bf.readLine())!=null){
             System.out.println(line);
         }
         //读取标准错误流
        BufferedReader brError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        String errline = null;
        while ((errline = brError.readLine())!=null){
            System.out.println(errline);
        }
        //判断进程是否停止
        int rs = proc.waitFor();
        if(rs !=0){
            return  R.error().put("msg","脚本执行错误,更新失败！");
        }
        } catch (Exception e) {
            return  R.error().put("msg","未知错误,更新失败！");
        }
        return  R.ok();
    }
}



