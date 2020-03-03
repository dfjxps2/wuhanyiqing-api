package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.utils.SSH;
import io.dfjinxin.config.SystemParams;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.service.DataTblService;
import io.dfjinxin.modules.wash.dto.FieldRuleDto;
import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;
import io.dfjinxin.modules.wash.entity.PrdDataFldWashProjEntity;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;
import io.dfjinxin.modules.wash.service.DataFldWashProjService;
import io.dfjinxin.modules.wash.service.PrdDataFldWashProjService;
import io.dfjinxin.modules.wash.service.PrdDataProcJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataProcJobDao;
import io.dfjinxin.modules.wash.entity.DataProcJobEntity;
import io.dfjinxin.modules.wash.service.DataProcJobService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;


@Service("dataProcJobService")
public class DataProcJobServiceImpl extends ServiceImpl<DataProcJobDao, DataProcJobEntity> implements DataProcJobService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final Integer PRD_JOB_STA_RUN = 0;
    private static final String JOB_STA_RUN = "RUNNING";
    private static final Integer TASK_TYPE = 1;
    private static final Integer TASK_STA_RUN = 0;
    private static final String TASK_SCRT_PUB = "1";
    private static final String TASK_SCRT_DEL = "0";
    private static final Integer SCHEDULE_TYPE_HAND = 0;
    private static final Integer SCHEDULE_TYPE_AUTO = 1;
    @Autowired
    private SystemParams systemParams;
    @Autowired
    private DataFldWashProjService dataFldWashProjService;
    @Autowired
    private PrdDataFldWashProjService prdDataFldWashProjService;
    @Autowired
    private DataTblService dataTblService;
    @Autowired
    private PrdDataProcJobService prdDataProcJobService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataProcJobEntity> page = this.page(
                new Query<DataProcJobEntity>().getPage(params),
                new QueryWrapper<DataProcJobEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryTaskList(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.queryTaskList(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPublishList(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.queryPublishList(getPager(params), params);
        return new PageUtils(page);
    }

    private Page getPager(Map<String, Object> params){
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        Page pager = new Page(no, limit);
        return pager;
    }

    /**
     * 执行作业脚本
     */
    @Override
    public R doRun(Map<String, Object> params) {
        //添加新作业
        R res = addPrdJob(params);
        if(!isok(res))
            return res;
        List<PrdDataProcJobEntity> list = (List<PrdDataProcJobEntity>)res.get("list");
        String dpType = mstr(params, "dpType");
        String startDt = mstr(params, "startDate");
        return execShEtlNow(list, dpType, startDt);
    }
    private R execShEtlNow(List<PrdDataProcJobEntity> list, String dpType, String startDt){
        String shell = systemParams.getShEtlNow();
        String sshMode = "1"; //正式清洗
        String rate = "100";  //100%
        //sh ?.sh jobid,dataTblid,dpType,rate,sshMode, dataSplPct,dataStartDt
        String tmpl = shell + " {0} {1} {2} {3} {4}";
        String prm0 = buildJobAndTblParm2(list);
        String cmds = MessageFormat.format(tmpl, prm0, dpType, sshMode, rate, startDt);
        SSH.execScript(cmds, systemParams);
        return R.ok("已立即执行，请等待结果");
    }

    /**
     * 添加并执行作业
     */
    @Override
    public R newRun(Map<String, Object> params, String cmd) {
        return R.ok();
    }

    /**
     * 添加并执行探查作业
     *
     * @param params
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R doExplorer(Map<String, Object> params) {
        //script: sh {sh-check} 123 1 1 0 10 20190101
        //mock: 作业ID  123
        //      表ID  1
        //      模式  1  1批量探查 0指定探查
        //      抽样百分比  10  代表10%抽样  全表100
        //      指定日期(可选)  20190101
        String shell = systemParams.getShCheck();
        //添加新作业
        R res = addJob(params);
        if(!isok(res))
            return res;
        String dpType = mstr(params, "dpType");
        String rate = mstr(params, "rate");
        String startDt = mstr(params, "startDate").replace("-", "");
        List<DataProcJobEntity> list = (List<DataProcJobEntity>)res.get("list");
        //sh ?.sh jobid,dataTblid,dpType,rate,dataSplPct,dataStartDt
        String tmpl = shell + " {0} {1} {2} {3}";
        String prm0 = buildJobAndTblParm(list);
        String cmds = MessageFormat.format(tmpl, prm0, dpType, rate, startDt);
        return SSH.execScript(cmds, systemParams);
    }

    private String buildJobAndTblParm(List<DataProcJobEntity> list){
        StringBuilder jsb = new StringBuilder(100);
        StringBuilder tsb = new StringBuilder(100);
        int i = 0;
        for(DataProcJobEntity m : list){
            if(i > 0){
                jsb.append(",");
                tsb.append(",");
            }
            jsb.append(m.getJobid().toString());
            tsb.append(m.getDataTblid().toString());
            i++;
        }
        return jsb.toString() + " " + tsb.toString();
    }
    private String buildJobAndTblParm2(List<PrdDataProcJobEntity> list){
        StringBuilder jsb = new StringBuilder(100);
        StringBuilder tsb = new StringBuilder(100);
        int i = 0;
        for(PrdDataProcJobEntity m : list){
            if(i > 0){
                jsb.append(",");
                tsb.append(",");
            }
            jsb.append(m.getJobid().toString());
            tsb.append(m.getDataTblid().toString());
            i++;
        }
        return jsb.toString() + " " + tsb.toString();
    }
    /**
     * 添加并执行清洗作业
     *
     * @param params
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R doWash(Map<String, Object> params) {
        //script: sh {sh-cleanse} 123 1 1 0 10 20190101
        //mock: 作业ID  123
        //      表ID  1
        //      模式  1  1批量探查 0指定探查
        //      清洗模式 0 0开发 1生产
        //      抽样百分比  10  代表10%抽样  全表100
        //      指定日期(可选)  20190101
        String shell = systemParams.getShCleanse();
        //添加新作业
        R res = addJob(params);
        if(!isok(res))
            return res;
        String sshMode = systemParams.getShCleanseMode();
        String dpType = mstr(params, "dpType");
        String rate = mstr(params, "rate");
        String startDt = mstr(params, "startDate");
        List<DataProcJobEntity> list = (List<DataProcJobEntity>)res.get("list");
        //sh ?.sh jobid,dataTblid,dpType,rate,sshMode, dataSplPct,dataStartDt
        String tmpl = shell + " {0} {1} {2} {3} {4}";
        String prm0 = buildJobAndTblParm(list);
        String cmds = MessageFormat.format(tmpl, prm0, dpType, sshMode, rate, startDt);
        return SSH.execScript(cmds, systemParams);
    }

    /**
     * 发布作业
     * @param params
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    @Override
    public R doPublish(Map<String, Object> params){
        //script: sh {sh-etl} 123 1 1
        //mock: 表ID  123
        //      作业类型  1  1清洗作业 2脱敏作业
        //      作业标识 1 1发布 0删除

        //第一次发布新增并执行脚本，以后的发布更新日期不执行脚本
        Integer scheduleType = mint(params,"scheduleType", -1);
        String dpType = mstr(params, "dpType");
        Integer jobType = mint(params, "jobType"); //发布作业类型 1-清洗作业
        String idstr = mstr(params, "ids");
        if(idstr.length() == 0)
            return R.error("请选择要发布的记录");
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        R res = R.ok("发布成功");
        int errCnt = 0;
        QueryWrapper<DataFldWashProjEntity> where1 = new QueryWrapper<>();
        where1.inSql("Fldid", "select Fldid from Data_Fld where Data_Tblid in (" + idstr + ")");
        List<DataFldWashProjEntity> sources = dataFldWashProjService.list(where1);
        List<PrdDataFldWashProjEntity> targets = new ArrayList<>();
        for(DataFldWashProjEntity s : sources){
            PrdDataFldWashProjEntity t = new PrdDataFldWashProjEntity();
            t.setDataWashCmpuid(s.getDataWashCmpuid());
            t.setExctOrd(s.getExctOrd());
            t.setFldid(s.getFldid());
            t.setParaid(s.getParaid());
            t.setReleaseTime(now);
            targets.add(t);
        }
        QueryWrapper<PrdDataFldWashProjEntity> where2 = new QueryWrapper<>();
        where2.inSql("Fldid", "select Fldid from Data_Fld where Data_Tblid in (" + idstr + ")");
        where2.and(w -> w.inSql("Data_Wash_Cmpuid","select Data_Wash_Cmpuid from data_wash_cmpu where Data_Wash_Cmpu_Type='0'"));
        QueryWrapper<DataTblEntity> where3 = new QueryWrapper<>();
        where3.inSql("Data_Tblid", idstr);
        QueryWrapper<PrdDataProcJobEntity> where4 = new QueryWrapper<>();
        where4.inSql("Data_Tblid", idstr).in("Job_Type", "1");
        List<DataTblEntity> tbls = dataTblService.list(where3);
        //将试清洗配置复制到发布清洗配置中
        prdDataFldWashProjService.remove(where2);
        prdDataFldWashProjService.saveBatch(targets);
        //生成发布作业,已发布的更新发布日期，未发布的直接添加;手工调度再添加立即作业，
        List<PrdDataProcJobEntity> jobs = prdDataProcJobService.list(where4);
        for(DataTblEntity m : tbls){
            PrdDataProcJobEntity t = findPrdProcJob(jobs, m.getDataTblid());
            R r = this.doPublishEach(t,m, now,jobType, scheduleType, dpType); //发布
            if(!isok(r)){
                errCnt++;
            }
        }
        if(errCnt > 0){
            res = R.error(errCnt+"个作业发布失败，请重试或联系管理员");
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R doPublishEach(PrdDataProcJobEntity t, DataTblEntity m, Date now, Integer jobType, Integer scheduleType, String dpType){
        Boolean bpublish = t == null ? true : false;
        Integer jobScheduleType = scheduleType;
        if(t == null){
            t = new PrdDataProcJobEntity();
            t.setDataTblid(m.getDataTblid());
            t.setJobType(jobType);
            if(jobScheduleType == -1){
                jobScheduleType = SCHEDULE_TYPE_HAND;
            }
            t.setJobScheduleType(jobScheduleType);
            t.setJobReleaseDate(now);
            prdDataProcJobService.saveOrUpdate(t);
        }else{
            t.setJobReleaseDate(now);
            if(t.getJobExpireDate() != null){
                bpublish = true;
                t.setJobExpireDate(null);
            }
            if(jobScheduleType != -1){
                t.setJobScheduleType(jobScheduleType);
            }else{
                jobScheduleType = t.getJobScheduleType();
            }
            baseMapper.updateRePublish(t.getDataTblid().toString(), jobScheduleType.toString());
        }
        //手工调度不同步到etl
        if(jobScheduleType != SCHEDULE_TYPE_AUTO){
            bpublish = false;
        }

        List<String> ids = new ArrayList<>();
        ids.add(t.getDataTblid().toString());
        R res = R.ok("发布成功");
        if(bpublish)
            res = buildPublishScript(ids, jobType, TASK_SCRT_PUB);
        return res;
    }

    private PrdDataProcJobEntity findPrdProcJob(List<PrdDataProcJobEntity> jobs, Integer dataTblid){
        for(PrdDataProcJobEntity job :  jobs){
            if(job.getDataTblid().equals(dataTblid))
                return job;
        }
        return null;
    }
    /**
     * 调用清洗脚本
     * @param ids  发布作业jobid数组
     * @param jobType 脚本类型 1-清洗 2-脱敏
     * @param flag 执行类型 0-发布 1-删除
     * @return
     */
    @Override
    public R buildPublishScript(List<String> ids, Integer jobType, String flag){
        String shell = systemParams.getShEtl();
        //sh dataTblid,jobType,flag
        String tmpl = shell + " {0} {1} {2} D 0";

        StringBuilder sb = new StringBuilder(100);
        sb.append("sh ");
        int i = 0;
        for(String dataTblid : ids){
            if(i > 0){
                sb.append(" && ");
            }
            sb.append(MessageFormat.format(tmpl, dataTblid, jobType, flag));
            i++;
        }
        String cmds = sb.toString();
        R res = SSH.execScript(cmds, systemParams);
        String returnCode = res.containsKey("returnCode") ? res.get("returnCode").toString() : "";
        if("1".equals(returnCode)) {
            res = R.error("发布脚本返回失败，请联系管理员");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
        }
        return res;
    }

    /**
     * 调用数据脱敏脚本
     * @param jobId  发布作业jobid数组
     * @return
     */
    public R buildTmScript(Integer jobId, String startDate, String endDate){
        String shell = systemParams.getShTm();
        //sh dataTblid,jobType,flag
        String tmpl = shell + " {0} {1} {2} D 0";
        StringBuilder sb = new StringBuilder(100);
        sb.append("sh ");
        sb.append(MessageFormat.format(tmpl, jobId, startDate, endDate));
        String cmds = sb.toString();
        R res = SSH.execScript(cmds, systemParams);
        return res;
    }

    /**
     * 删除发布作业
     * @param params
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    @Override
    public R delPublish(Map<String, Object> params){
        Integer jobType = mint(params, "jobType");
        String idstr = mstr(params, "ids");
        String[] ids = idstr.split(",");
        List<Integer> jobids = new ArrayList();
        List<String> jobarr = new ArrayList<>();
        QueryWrapper<PrdDataProcJobEntity> where = new QueryWrapper<>();
        where.inSql("Data_Tblid", idstr);
        List<PrdDataProcJobEntity> jobs = prdDataProcJobService.list(where);
        int errCnt = 0;
        for(String id : ids){
            R r = delPublishEach(id, jobType, jobs);
            if(!isok(r))
                errCnt++;
        }
        R res = errCnt > 0 ? R.error("有"+errCnt+"个作业无法禁用，请联系管理员") : R.ok("操作成功");
        return res;
        //删除字段配置
////        QueryWrapper<PrdDataFldWashProjEntity> where2 = new QueryWrapper<>();
////        where2.inSql("Fldid", "select Fldid from Data_Fld where Data_Tblid in (select Data_Tblid from prd_data_proc_job where Jobid in (" + idstr + "))");
////        where2.and(w -> w.inSql("Data_Wash_Cmpuid","select Data_Wash_Cmpuid from data_wash_cmpu where Data_Wash_Cmpu_Type='0'"));
////        prdDataFldWashProjService.remove(where2);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R delPublishEach(String tblid,Integer jobType, List<PrdDataProcJobEntity> jobs) {
        PrdDataProcJobEntity ppj = findPrdProcJob(jobs, Integer.valueOf(tblid));
        boolean b = baseMapper.updateDelPublish(tblid);
        if(!b) return R.error("保存记录失败");
        //手工调度不同步etl
        Integer scheduleType = ppj == null ? SCHEDULE_TYPE_AUTO : ppj.getJobScheduleType(); //1.自动 0手工
        if(scheduleType == SCHEDULE_TYPE_HAND){
            return R.ok();
        }
        List<String> ids = new ArrayList<>();
        ids.add(tblid);
        R res = this.buildPublishScript(ids,jobType, TASK_SCRT_DEL);
        return res;
    }

    /**
     * 添加新作业(删除对应旧作业)
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R addJob(Map<String, Object> params) {
        String idstr = mstr(params,"ids");
        String startstr = mstr(params, "startDate");
        String endstr = mstr(params, "endDate");
        Integer rate = mint(params, "rate");
        Integer jobType = mint(params, "jobType");
        String[] ids = idstr.split(",");
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date startDt = null, endDt = null;
        try{
            if(startstr.length()>0) {
                startDt = df.parse(startstr);
                endDt = startDt;//df.parse(endstr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return R.error("数据日期格式不正确，2019-06-01");
        }
        Calendar calendar = Calendar.getInstance();
        Date st = calendar.getTime();
        QueryWrapper<DataProcJobEntity> oldParm = new QueryWrapper<>();
        //同类型作业执行时间小于10分钟的不能再执行
        oldParm.in("Data_Tblid", (Object[])ids);
        oldParm.and(w -> w.eq("Job_Stus", JOB_STA_RUN));
        oldParm.and(w -> w.eq("Job_Type", jobType));
        oldParm.and(w -> w.lt("timestampdiff(hour, Start_Tm, NOW())", "1"));
        List<DataProcJobEntity> allList = baseMapper.selectList(oldParm);

        //如果全在执行中，不允许重新执行
        if(allList.size() == ids.length){
            return R.error("任务仍在执行中，请等待");
        }
        //新增作业
        List<DataProcJobEntity> list = new ArrayList<>();
        for(String id : ids){
            DataProcJobEntity entity = new DataProcJobEntity();
            Integer tblid = Integer.valueOf(id);
            //Schd_Jobid(etl_jobid)
            entity.setDataTblid(tblid);
            entity.setJobStus(JOB_STA_RUN);
            entity.setJobType(jobType);
            entity.setStartTm(st);
            entity.setRfrshTm(st);
            entity.setDataStartDt(startDt);
            entity.setDataTerminateDt(endDt);
            entity.setDataSplPct(rate);

            list.add(entity);
        }

        this.saveOrUpdateBatch(list);
        return R.ok().put("list", list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public R addPrdJob(Map<String, Object> params) {
        String idstr = mstr(params,"ids");
        String startstr = mstr(params, "startDate");
        String endstr = mstr(params, "endDate");
        Integer rate = 100;
        Integer jobType = mint(params,"jobType");
        String[] ids = idstr.split(",");
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date startDt = null, endDt = null;
        try{
            if(startstr.length()>0) {
                startDt = df.parse(startstr);
                endDt = startDt;//df.parse(endstr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return R.error("数据日期格式不正确，2019-06-01");
        }
        Calendar calendar = Calendar.getInstance();
        Date st = calendar.getTime();
        QueryWrapper<PrdDataProcJobEntity> oldParm = new QueryWrapper<>();
        //同类型作业执行时间小于10分钟的不能再执行
        oldParm.in("Data_Tblid", (Object[])ids);
        oldParm.and(w -> w.eq("Job_Exec_Status", PRD_JOB_STA_RUN));
        //oldParm.and(w -> w.eq("Job_Type", jobType));
        oldParm.and(w -> w.lt("timestampdiff(hour, Job_Start_Time, NOW())", "1"));
        List<PrdDataProcJobEntity> allList = prdDataProcJobService.list(oldParm);

        //如果全在执行中，不允许重新执行
        if(allList.size() == ids.length){
            return R.error("任务仍在执行中，请等待");
        }
        //新增作业
        List<PrdDataProcJobEntity> list = new ArrayList<>();
        for(String id : ids){
            PrdDataProcJobEntity entity = new PrdDataProcJobEntity();
            Integer tblid = Integer.valueOf(id);
            //Schd_Jobid(etl_jobid)
            entity.setDataTblid(tblid);
            entity.setJobType(jobType);
            entity.setJobReleaseDate(st);
            entity.setJobStartTime(st);
            entity.setJobExecStatus(PRD_JOB_STA_RUN);//正在执行
            list.add(entity);
        }
        prdDataProcJobService.saveOrUpdateBatch(list);
        return R.ok().put("list", list);
    }
    /**
     * 执行SSH远程脚本
     * @param cmd
     */
    public R execSSH(String cmd) {
        String host = systemParams.getSshHost();
        String user = systemParams.getSshUser();
        String pass = systemParams.getSshPwd();

        Thread execThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ssh:Run of Runnable:" + cmd);
                SSH ssh = new SSH(host, user, pass, 22);
                R res = ssh.exec(cmd);
                System.out.println("ssh:result:"+res.get("code")+","+res.get("msg"));
            }
        }) {
            public void run() {
                System.out.println("ssh:开始执行SSH远程脚本");
                super.run();
            }
        };

        try {
            execThread.start();
            execThread.sleep(3000);
            execThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return R.ok("启动成功");
    }


    /**
     * 查询字段清洗项目
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryTblRule(Map<String, Object> params) {
        IPage<FieldRuleDto> page = baseMapper.queryTblRule(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryTaskRule(Map<String, Object> params) {
        IPage<FieldRuleDto> page = baseMapper.queryTaskRule(getPager(params), params);
        return new PageUtils(page);
    }

    /**
     * 保存字段清洗项目
     *
     * @param list
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public R saveTblRule(List<FieldRuleDto> list) {
        List<DataFldWashProjEntity> washList = new ArrayList<>();
        List<Integer> fldIdList = new ArrayList<>();
        for(FieldRuleDto dto : list){
            washList.addAll(dto.getWashList());
            Integer fid = dto.getFldid();
            boolean b = false;
            for(Integer i : fldIdList){
                if(fid.equals(i)){
                    b = true;
                    break;
                }
            }
            if(!b){
                fldIdList.add(fid);
            }
        }
        //删除旧配置再新增保存
        QueryWrapper<DataFldWashProjEntity> where = new QueryWrapper<>();
        where.in("fldid", fldIdList);
        dataFldWashProjService.remove(where);
        dataFldWashProjService.saveBatch(washList);
        return R.ok();
    }

    /**
     * 获取清洗规则
     *
     * @param m
     */
    @Override
    public List<Map<String, Object>> getWashRules(Map<String, Object> m) {
        return baseMapper.getWashRules(m);
    }


    private boolean isok(R r){
        String code = mstr(r,"code");
        return code.equals("0");
    }
    private String mstr(Map<String,Object> param, String key){
        if(!param.containsKey(key))
            return "";
        Object obj = param.get(key);
        if(obj == null)
            return "";
        return obj.toString();
    }
    private Integer mint(Map<String,Object> param, String key){
        String val = mstr(param, key);
        return val.equals("") ? 0 : Integer.valueOf(val);
    }
    private Integer mint(Map<String,Object> param, String key, Integer dfv){
        String val = mstr(param, key);
        return val.equals("") ? dfv : Integer.valueOf(val);
    }
}