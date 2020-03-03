package io.dfjinxin.modules.storage.service.impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dfjinxin.common.aspect.log.SysJsonUtils;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.DateTools;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.dao.DataPartDao;
import io.dfjinxin.modules.meta.dao.DataPartTypeDao;
import io.dfjinxin.modules.meta.dao.DbDao;
import io.dfjinxin.modules.meta.dto.DataPartDto;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.storage.constance.StorageCon;
import io.dfjinxin.modules.storage.dao.DataDstzDao;
import io.dfjinxin.modules.storage.dao.DesenDao;
import io.dfjinxin.modules.storage.dto.*;
import io.dfjinxin.modules.storage.entity.DataDstzEntity;
import io.dfjinxin.modules.storage.service.DataDstzService;
import io.dfjinxin.modules.storage.service.DesenService;
import io.dfjinxin.modules.storage.utils.StorageUtils;
import io.dfjinxin.modules.wash.entity.DataFldWashProjEntity;
import io.dfjinxin.modules.wash.entity.PrdDataFldWashProjEntity;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;
import io.dfjinxin.modules.wash.entity.WashCmpuParaEntity;
import io.dfjinxin.modules.wash.service.*;
import io.dfjinxin.modules.wash.service.impl.DataProcJobServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DesenServiceImpl implements DesenService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public PageUtils query(DesenForm desenForm, Long tenantId) {

//        desenForm.setDbUsageCd(StorageUtils.Db_Usage_Cd);//设置只能查询清洗用途库

        if(desenForm.getPartid() == null || desenForm.getPartid() == 0){
            throw new RRException("数据分区不能为空！");
        }

        List<PartDto> partDtos = this.queryDataPartType(tenantId.intValue());
        if(!StorageUtils.isMyPart(partDtos, desenForm.getPartid())){
            throw new RRException("数据分区：" + desenForm.getPartid() + "未归属本租户！");
        }

        int totalCount = desenDao.queryDesenDataCount(desenForm);
        List<DataTblEntity> list = desenDao.queryDesenDataList(desenForm);
        return new PageUtils(list, totalCount, desenForm.getPageSize(), desenForm.getPageIndex());//List<?> list, int totalCount, int pageSize, int currPage;
    }

    @Override
    public List<PartDto> queryDataPartType(Integer tenantId){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Tnmtid", tenantId);
//        map.put("partTypeids", Stream.of(StorageUtils.Part_Type_YS, StorageUtils.Part_Type_RH).collect(Collectors.toList()));
        map.put("partTypeids", Arrays.asList(StorageUtils.Part_Type_YS, StorageUtils.Part_Type_RH));
        return dataPartTypeDao.queryDataPartRestrict(map);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveDataFldWashProj(SaveDesen saveDesen){
        /**
         * 用户在字段上配置的脱敏运算记录在“数据字段清洗项目”表中
         */

        //删除原有的
        desenDao.deleteDataFldWashProj(saveDesen.getTbId());

        //所有字段都不设置清洗算法时将周期任务设置成失效
        boolean isExpire = true;
        //保存重新设置的
        List<DataFldWashProjEntity> es = new ArrayList<DataFldWashProjEntity>();
        for(DesenFld fld : saveDesen.getDesenFlds()){
            if(fld.getDataWashCmpuid() > 0){
                isExpire = false;
                DataFldWashProjEntity e = new DataFldWashProjEntity();
                e.setFldid(fld.getFldid());//字段ID
                e.setDataWashCmpuid(fld.getDataWashCmpuid());//数据清洗运算ID

                List<WashCmpuParasDto> ws = fld.getWashCmpuParas();
                if(null!= ws){
                    WashCmpuParaEntity washCmpuPara = new WashCmpuParaEntity();
                    washCmpuPara.setParaValue(SysJsonUtils.objectToJson(ws));
                    washCmpuParaService.save(washCmpuPara);
                    e.setParaid(washCmpuPara.getParaid());
                }
                es.add(e);
            }
        }
        if(StorageUtils.JOB_TYPE_ZQ == saveDesen.getJobType() && isExpire){
            QueryWrapper queryWrapper = new QueryWrapper<PrdDataProcJobEntity>();
            queryWrapper.eq("Data_Tblid", saveDesen.getTbId());
            queryWrapper.in("Job_Type", StorageUtils.JOB_TYPE_ZQ);
            List<PrdDataProcJobEntity> proList = prdDataProcJobService.list(queryWrapper);
            if(null != proList && proList.size() > 0){
                PrdDataProcJobEntity e = proList.get(0);
                e.setJobExpireDate(new Date());
                prdDataProcJobService.updateById(e);
            }
        }

        String czJob = "";
        if(es.size() > 0){
            dataFldWashProjService.saveBatch(es);
        }else{
            czJob = "01";
        }
        return czJob;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void czPrdDataProcJob(SaveDesen saveDesen){
        //查询之前是否存在
        QueryWrapper queryWrapper = new QueryWrapper<PrdDataProcJobEntity>();
        queryWrapper.eq("Data_Tblid", saveDesen.getTbId());
//            queryWrapper.in("Job_Type", StorageUtils.JOB_TYPE_ZQ, StorageUtils.JOB_TYPE_LJ);
        queryWrapper.in("Job_Type", StorageUtils.JOB_TYPE_ZQ);
        queryWrapper.isNull("Job_Expire_Date");
        List<PrdDataProcJobEntity> proList = prdDataProcJobService.list(queryWrapper);
        //如果存在周期任务，置为无效
        for(PrdDataProcJobEntity e : proList){
            //id传值是tableid
            dataProcJobService.buildPublishScript(StorageUtils.idToIds(e.getDataTblid()), StorageUtils.JOB_TYPE_ZQ, StorageUtils.CZ_TYPE_DEL);
            e.setJobExpireDate(new Date());
            prdDataProcJobService.updateById(e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SaveDesen savePro(SaveDesen saveDesen){
        Date date = new Date();
        /**
         * 用户在字段上配置的脱敏运算记录在“数据字段清洗项目”表中
         */
        String czJob = this.saveDataFldWashProj(saveDesen);
        saveDesen.setCzJob(czJob);

        //3、用户在字段上配置的脱敏运算记录在“数据字段清洗项目”表中，如果用户选择“周期执行脱敏”，则将配置记录在“已发布数据字段清洗项目”prd_data_fld_wash_proj表中表中。
        //5、如果用户选择“周期执行脱敏”，则为每个需要脱敏的表在“已发布数据处理作业”prd_data_proc_job表中插入一条记录，
        // 作业类型设置为“周期执行脱敏作业”，并将相关字段的脱敏配置填入“已发布数据字段清洗项目”表，然后调用后台脚本注册周期脱敏作业，传入作业ID。

        if(StorageUtils.JOB_TYPE_ZQ == saveDesen.getJobType()){
            //删除原有的
            desenDao.deletePrdDataFldWashProj(saveDesen.getTbId());

            //保存重新设置的
            List<PrdDataFldWashProjEntity> prds = new ArrayList<PrdDataFldWashProjEntity>();
            for(DesenFld fld : saveDesen.getDesenFlds()){
                if(fld.getDataWashCmpuid() > 0){
                    PrdDataFldWashProjEntity e = new PrdDataFldWashProjEntity();
                    e.setFldid(fld.getFldid());//字段ID
                    e.setDataWashCmpuid(fld.getDataWashCmpuid());//数据清洗运算ID
                    e.setReleaseTime(date);//清洗规则发布时间
                    prds.add(e);
                }
            }
            if(prds.size() > 0){
                prdDataFldWashProjService.saveBatch(prds);
            }
        }

        //如果选择“立即执行脱敏”，
        // 并选择了数据日期范围，则为每个需要脱敏的表在“已发布数据处理作业”表中插入一条记录，
        // 作业类型设置为“立即执行脱敏作业”，并调用后台脱敏脚本，
        // 传入作业ID、数据日期范围，脚本从”数据字段清洗项目“表中获得字段的清洗配置，
        // 依次为数据日期范围内的每个日期执行指定的数据脱敏，
        // 并将结果写入脱敏库的相应日期分区，
        // 在“已发布作业字段清洗结果”表中记录该作业在各字段上的该日期数据上的执行结果，
        // 最后将“已发布数据处理作业”表中的“作业执行状态”字段设置为“已完成”。
        if(StorageUtils.JOB_TYPE_LJ == saveDesen.getJobType()){

        }

        //将所有字段的脱敏设置无效，不设置脱敏算法
        if("01".equals(saveDesen.getCzJob())){
            this.czPrdDataProcJob(saveDesen);
        }else{

            boolean insertFlag = true;//新插入一条数据标志位
            if(StorageUtils.JOB_TYPE_ZQ == saveDesen.getJobType()){
                //周期执行查看之前是否有存在，有则修改，没有则插入一条
                //查询之前是否存在
                QueryWrapper queryWrapper = new QueryWrapper<PrdDataProcJobEntity>();
                queryWrapper.eq("Data_Tblid", saveDesen.getTbId());
                queryWrapper.in("Job_Type", StorageUtils.JOB_TYPE_ZQ);
                List<PrdDataProcJobEntity> proList = prdDataProcJobService.list(queryWrapper);
                if(null != proList && proList.size() > 0){
                    PrdDataProcJobEntity e = proList.get(0);
                    if(null == e.getJobExpireDate()){
                        saveDesen.setCzJob("11");//已存在
                    }else{
                        e.setJobExpireDate(null);
                    }
                    e.setJobReleaseDate(new Date());
                    prdDataProcJobService.updateJobById(e);

                    insertFlag = false;//已存在，不再插入
                    saveDesen.setJobId(e.getJobid());
                }
            }

            if(insertFlag){
                //插入一条数据
                PrdDataProcJobEntity pe = new PrdDataProcJobEntity();
                pe.setJobType(saveDesen.getJobType());//作业类型
                pe.setDataTblid(saveDesen.getTbId());//数据表ID
                pe.setJobReleaseDate(date);//作业发布日期
                //pe.setjobExecStatus;//作业执行状态0：正在执行1：执行完成 2：执行失败
                //pe.setJobStartTime();//作业启动时间
                //pe.setJobFinishTime();//作业结束时间
                prdDataProcJobService.save(pe);
                saveDesen.setJobId(pe.getJobid());
            }
        }
        return saveDesen;
    }

    @Override
    public String setting(SaveDesen saveDesen){
        if (saveDesen.getJobStartTime().compareTo(saveDesen.getJobFinishTime()) > 0) {
            throw new RRException("开始日期不能大于结束日期");
        }
        saveDesen = this.savePro(saveDesen);
        this.distanceSsh(saveDesen);
        return saveDesen.getCzJob();
    }

    private void distanceSsh(SaveDesen saveDesen){

        logger.info("开始执行脱敏过程：saveDesen: {}", saveDesen);
        R resPublic = null;

        //CzJob为01，无需再做操作，01为设置所有字段为不脱敏，在之前的操作已经把存在的周期脱敏任务置无效
        if("01".equals(saveDesen.getCzJob())){

        }else if(StorageUtils.JOB_TYPE_LJ == saveDesen.getJobType()){
            //立即执行，id传值的是作业id
            resPublic = dataProcJobService.buildTmScript(saveDesen.getJobId(),DateTools.toString(saveDesen.getJobStartTime(), "yyyyMMdd"),DateTools.toString(saveDesen.getJobFinishTime(), "yyyyMMdd"));

        }else if(StorageUtils.JOB_TYPE_ZQ == saveDesen.getJobType()){

            if("11".equals(saveDesen.getCzJob())){
                //已存在有效的周期脱敏，不再调用发布任务
            }else{
                //周期执行，id传值的是tableid
                resPublic = dataProcJobService.buildPublishScript(StorageUtils.idToIds(saveDesen.getTbId()), StorageUtils.JOB_TYPE_ZQ, StorageUtils.CZ_TYPE_PUB);
            }
        }

        logger.info("脱敏过程执行结束：saveDesen: {}", saveDesen);
    }

    @Override
    public List<PrdDataProcJobEntity> queryHistory(Integer tbId){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("data_Tblid", tbId);
        queryWrapper.in("job_Type", StorageUtils.JOB_TYPE_LJ, StorageUtils.JOB_TYPE_ZQ);
        queryWrapper.orderByDesc("Job_Release_Date");
        return prdDataProcJobService.list(queryWrapper);
    }

    @Override
    public void deleteDesen(Integer[] deleteIds){
        dataDstzService.removeByIds(Arrays.asList(deleteIds));
    }

    @Override
    public R queryFldByTbId(Integer tbId){
        R r = R.ok();
        List<DesenFld> desenFlds = desenDao.queryFldByTbId(tbId);
        if(null != desenFlds){
            for(DesenFld d : desenFlds){
                if(null != d.getParaid()){
                    WashCmpuParaEntity washCmpuParaEntity = washCmpuParaService.getById(d.getParaid());
                    if(null != washCmpuParaEntity && null != washCmpuParaEntity.getParaValue()){
                        d.setWashCmpuParas(SysJsonUtils.jsonToWashCmpuParas(washCmpuParaEntity.getParaValue()));
                    }
                }
            }
        }
        r.put("list", desenFlds);

        List<PrdDataProcJobEntity> lists = this.queryHistory(tbId);
        if(null != lists && lists.size() > 0){
            r.put("jobType", lists.get(0).getJobType());
        }
        return r;
    }

    @Override
    public List<DbEntity> queryDbsRestrict(Map<String , Object> map){
        return dbDao.queryDbsRestrict(map);
    }
    @Autowired
    private DataProcJobService dataProcJobService;
    @Autowired
    private DbDao dbDao;
    @Autowired
    private PrdDataProcJobService prdDataProcJobService;
    @Autowired
    private PrdDataFldWashProjService prdDataFldWashProjService;
    @Autowired
    private DataFldWashProjService dataFldWashProjService;
    @Autowired
    private DataPartTypeDao dataPartTypeDao;
    @Autowired
    private DataDstzService dataDstzService;
    @Autowired
    private DesenDao desenDao;
    @Autowired
    private WashCmpuParaService washCmpuParaService;
}
