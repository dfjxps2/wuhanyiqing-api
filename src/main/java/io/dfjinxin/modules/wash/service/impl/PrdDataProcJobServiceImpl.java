package io.dfjinxin.modules.wash.service.impl;

import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.SSH;
import io.dfjinxin.config.SystemParams;
import io.dfjinxin.modules.hive.service.HiveService;
import io.dfjinxin.modules.meta.dao.DataFldDao;
import io.dfjinxin.modules.meta.dao.DataTblDao;
import io.dfjinxin.modules.meta.dao.DbDao;
import io.dfjinxin.modules.meta.dto.DataTblDto;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.wash.dao.PrdDataProcJobDao;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;
import io.dfjinxin.modules.wash.service.PrdDataProcJobService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;


@Service("prdDataProcJobService")
public class PrdDataProcJobServiceImpl extends ServiceImpl<PrdDataProcJobDao, PrdDataProcJobEntity> implements PrdDataProcJobService {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataTblDao dataTblDao;
    @Autowired
    private DbDao dbDao;
    @Autowired
    private DataFldDao dataFldDao;
    @Autowired
    private HiveService hiveService;
    @Autowired
    private SystemParams systemParams;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrdDataProcJobEntity> page = this.page(
                new Query<PrdDataProcJobEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据使用用途获取hive表全名
     *
     * @param dataTblid
     * @param dbUsageid 1汇聚库 2历史库 3清洗库 4问题库 5临时库 6脱敏库 7基础库 8主题库
     * @return
     */
    @Override
    public DataTblDto getHiveTable(Integer dataTblid, Integer dbUsageid, Integer jobid, Long Tnmtid) {
        DataTblDto dto = new DataTblDto();
        DataTblEntity tbl = dataTblDao.selectById(dataTblid);
        DbEntity db = dbDao.selectOne(new QueryWrapper<DbEntity>()
                .eq("Db_Usageid",dbUsageid).inSql("Partid","select Partid from data_part where Tnmtid="+Tnmtid));
        List<DataFldEntity> flds = dataFldDao.selectList(new QueryWrapper<DataFldEntity>()
                .eq("Data_Tblid", dataTblid).orderByAsc("Fld_Ord").isNull("Del_Dt"));
        String fname = (tbl == null && db == null) ? "" : db.getDbPhysNm() + "." + tbl.getDataTblPhysNm();
        if(dbUsageid.equals(4) && jobid > 0){
            fname += "_"+jobid.toString();
        }

        dto.setTbl(tbl);
        dto.setDb(db);
        dto.setFldList(flds);
        dto.setFullName(fname);
        return dto;
    }

    @Override
    public String getHiveSelectSql(DataTblDto dto, String where) {
        return getHiveSelectSql(dto, "", where);
    }

    @Override
    public String getHiveSelectSql(DataTblDto dto, String column, String where) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("select ");
        int i = 0;
        for(DataFldEntity fld : dto.getFldList()){
            if(i>0)
                sb.append(",");
            sb.append(fld.getFldPhysNm());
            i++;
        }
        if(StringUtils.isNotEmpty(column))
            sb.append(",").append(column);
        sb.append(" from ").append(dto.getFullName());
        if(StringUtils.isNotEmpty(where))
            sb.append(" where 1=1 ").append(where);
        return sb.toString();
    }

    @Override
    public String getHivePageSql(DataTblDto dto, String column, String where, Integer pageIndex, Integer pageLimit) {
        String sortStr = getPrikey(dto);
        if(pageIndex == 0)
            pageIndex = 1;
        if(pageIndex == 1){
            String baseSql = getHiveSelectSql(dto,  column, where);
            String firstSql = baseSql + " order by " + sortStr + " limit " + pageLimit.toString();
            return firstSql;
        }
        String pageColumn = "row_number() over (order by " + sortStr + ") as rnum";
        String newColumn = StringUtils.isNotEmpty(column) ? column + "," + pageColumn : pageColumn;
        String baseSql = getHiveSelectSql(dto,  newColumn, where);
        String pageSql = "select * from ("
                       + baseSql
                       + ") x where 1=1 ";
        Integer start = (pageIndex - 1) * pageLimit + 1;
        Integer end = start + pageLimit - 1;
        pageSql += MessageFormat.format(" and rnum between {0} and {1} ", start.toString(), end.toString());
        return pageSql;
    }

    @Override
    public String getHiveCountSql(String tbl, String where) {
        StringBuilder sb = new StringBuilder("select count(1) from " + tbl);
        if(StringUtils.isNotEmpty(where))
            sb.append(" where ").append(where);
        return sb.toString();
    }

    @Override
    public void checkHiveException(Exception e){
        RRException ex = checkHiveException2(e, true);
        if(ex != null)
            throw ex;
        else{
            throw new RRException("查询失败：数据库中不存在该表");
        }
    }

    @Override
    public RRException checkHiveException2(Exception e, boolean isUpdateMeta){
        e.printStackTrace();
        if (StringUtils.isEmpty(e.getMessage())) {
            return new RRException("查询无数据", e);
        }
        if (e.getMessage().contains("Unknown column")) {
            return new RRException("查询失败：该表字段存在错误");
        }
        if (e.getMessage().contains("SELECT command denied to user")) {
            return new RRException("查询失败：用户没有访问数据库权限");
        }
        if (e.getMessage().contains("Connection")) {
            return new RRException("查询失败：无法连接服务器，请联系管理员");
        }
        if (e.getMessage().contains("Could not resolve table reference:") || e.getMessage().contains("doesn't exist")) {
            //查询不到表时首先执行同步指令再查询一下
            return updateMeta(isUpdateMeta, "数据库中不存在该表");
        }
        return updateMeta(isUpdateMeta, e.getMessage());
        //return new RRException("查询失败：无法连接服务器，请重试或联系管理员");//+e.getMessage()
    }

    private RRException updateMeta(boolean isUpdateMeta, String message){
        if(!isUpdateMeta)
            return new RRException(message);
        StringBuilder sb = new StringBuilder(100);
        sb.append("sh ");
        String shRefresh = systemParams.getShRefresh();
        sb.append(shRefresh);
        logger.info("执行同步数据表命令:{}", shRefresh);
        try {
            SSH.execScript(sb.toString(), systemParams);
        }catch (Exception ee){
            logger.error("执行同步数据表命令:{}异常：{}", shRefresh, ee);
        }
        return new RRException("查询失败，请重试或联系管理员："+message);
    }


    @Override
    public List<String> getPrimaryKeys(DataTblDto dto){
        List<String> keys = new ArrayList<>();
        for(DataFldEntity fld : dto.getFldList()){
            if(fld.getIfPk() != null && "1".equals(fld.getIfPk().toString())){
                keys.add(fld.getFldPhysNm());
            }
        }
        if(keys.size() == 0)
            keys.add(dto.getFldList().get(0).getFldPhysNm());
        return keys;
    }

    @Override
    public List<DataFldEntity> getPrimaryFlds(DataTblDto dto){
        List<DataFldEntity> keys = new ArrayList<>();
        for(DataFldEntity fld : dto.getFldList()){
            if(fld.getIfPk() != null && "1".equals(fld.getIfPk().toString())){
                keys.add(fld);
            }
        }
        if(keys.size() == 0)
            keys.add(dto.getFldList().get(0));
        return keys;
    }

    @Override
    public List<Map<String, Object>> selectDataRepeate(String sql) {
        List<Map<String, Object>> list = null;
        try{
            list = hiveService.selectData(sql);
            return list;
        }catch(Exception ex){
            RRException rex = this.checkHiveException2(ex, true);
            if(rex == null){
                list = hiveService.selectData(sql);
                return list;
            }else{
                throw rex;
            }
        }
    }

    @Override
    public String tagConvert(Map<String, Object> m, String tags, List<DataFldEntity> fldList, List<DataWashCmpuEntity> cmpuList) {
        if(StringUtils.isEmpty(tags) || "rowid".equals(tags))
            return "";
        String[] vals = tags.split("<*>");
        if(vals.length == 0)
            return tags;
        StringBuilder sb = new StringBuilder(20);

        for(String val : vals){
            sb.append(",");
            String v = val.replace("<", "").replace(">", "").replace("#",":");
            String[] kvp = v.split(":");
            if(kvp.length != 2){
                sb.append(v);
                continue;
            }
            String typ = kvp[0];
            String fld = kvp[1];
            String fldcn = findByPhysName(fldList, fld);
            DataWashCmpuEntity c1 = findByJudge(cmpuList, typ);
            if(c1 == null){
                c1 = findByCd(cmpuList, typ);
            }
            if(c1 == null){
                sb.append(v);
            }else{
                sb.append(fldcn).append(":").append(c1.getDataWashCmpuDesc());
            }
        }
        return sb.length() > 1 ? sb.substring(1).toString() : "";
    }

    private String findByPhysName(List<DataFldEntity> list, String value){
        for(DataFldEntity c : list){
            if(value.equals(c.getFldPhysNm()) && c.getFldCnNm() != null && c.getFldCnNm().length() > 0)
                return c.getFldCnNm();
        }
        return value;
    }

    private DataWashCmpuEntity findByJudge(List<DataWashCmpuEntity> cmpuList, String value){
        for(DataWashCmpuEntity c : cmpuList){
            if(value.equals(c.getDataWashCmpuJudgeExpr()))
                return c;
        }
        return null;
    }
    private DataWashCmpuEntity findByCd(List<DataWashCmpuEntity> cmpuList, String value){
        for(DataWashCmpuEntity c : cmpuList){
            if(value.equals(c.getDataWashCmpuCd()))
                return c;
        }
        return null;
    }

    private String getPrikey(DataTblDto dto){
        List<String> keys = getPrimaryKeys(dto);
        return StringUtils.join(keys, ",");
    }

    private String getPageColumn(DataTblDto dto, String usrColumn){
        return null;
    }

    @Override
    public void updateJobById(PrdDataProcJobEntity procJobEntity) {
        baseMapper.updateJobById(procJobEntity);
    }
}