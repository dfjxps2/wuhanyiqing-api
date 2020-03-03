package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.utils.excel.ExportExcel;
import io.dfjinxin.modules.hive.service.HiveService;
import io.dfjinxin.modules.meta.dto.DataTblDto;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.wash.dao.PrdDataWashFldResultDao;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.entity.PrdDataWashFldResultEntity;
import io.dfjinxin.modules.wash.service.DataWashCmpuService;
import io.dfjinxin.modules.wash.service.PrdDataProcJobService;
import io.dfjinxin.modules.wash.service.PrdDataWashFldResultService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("prdDataWashFldResultService")
public class PrdDataWashFldResultServiceImpl extends ServiceImpl<PrdDataWashFldResultDao, PrdDataWashFldResultEntity> implements PrdDataWashFldResultService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HiveService hiveService;
    @Autowired
    private PrdDataProcJobService prdDataProcJobService;
    @Autowired
    private DataWashCmpuService dataWashCmpuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PrdDataWashFldResultEntity> page = this.page(
                new Query<PrdDataWashFldResultEntity>().getPage(params),
                new QueryWrapper<PrdDataWashFldResultEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<PrdDataWashFldResultEntity> queryDataByJobid(Integer jobid){
        return this.baseMapper.queryDataByJobid(jobid);
    }

    @Override
    public PageUtils washReport(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.washReport(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public PageUtils fieldProInfo(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.fieldProInfo(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> washInfo(Map<String, Object> params) {
        return baseMapper.washInfo(params);
    }

    @Override
    public List<Map<String,Object>> washProj(Map<String, Object> params) {
        return baseMapper.washProj(params);
    }

      @Override
    public List<Map<String,Object>> dataSrcList(Map<String, Object> params) {
        return baseMapper.dataSrcList(params);
    }

    @Override
    public List<Map<String,Object>> tableFld(Map<String, Object> params) {
        return baseMapper.tableFld(params);
    }

    @Override
    public R tableFldData(Map<String, Object> params) {
        try {
            String date1 = mstr(params, "date1");
            String date2 = mstr(params, "date2");
            Integer dataTblid = mint(params,"tabId");
            Long tnmtid = Long.valueOf(mstr(params,"tnmtid"));
            Integer total = mint(params, "total");
            if(StringUtils.isEmpty(date1) || StringUtils.isEmpty(date2)){
                return R.error("查询日期不能为空");
            }
            DataTblDto dto = prdDataProcJobService.getHiveTable(dataTblid, PrdDataProcJobService.DB_USAGEID_ISUDB, 0, tnmtid);

            int pageIndex = Integer.parseInt(params.get("page").toString());
            int pageLimit = Integer.parseInt(params.get("limit").toString());
            String whereStr = " and data_dt>='" + date1 + "' and data_dt<='" + date2 + "'";
            String querySql = prdDataProcJobService.getHivePageSql(dto
                    , "data_dt,tagslwq,rowidlwq,(case when isu_type='1' or isu_type='3' then '全字段重复' when isu_type='2' then '无法清洗' when isu_type='4' then '主键重复' when isu_type='1' then '' else isu_type end) isu_type"
                    , whereStr, pageIndex, pageLimit);
            String countSql = "select count(*) as count from " + dto.getFullName() + " where 1=1 " + whereStr; //查询总数

            Integer count = total > 0 ? total : Integer.parseInt(prdDataProcJobService.selectDataRepeate(countSql).get(0).get("count") + "");
            List<Map<String, Object>> list = prdDataProcJobService.selectDataRepeate(querySql);
            //转换描述
            List<DataWashCmpuEntity> cmpuList = dataWashCmpuService.list(new QueryWrapper<DataWashCmpuEntity>().eq("Data_Wash_Cmpu_Type","0"));
            for(Map<String, Object> m : list){
                m.put("tagslwq",prdDataProcJobService.tagConvert(m, mstr(m, "tagslwq"), dto.getFldList(), cmpuList));
            }
            PageUtils page = new PageUtils(list, count,pageLimit, count);

            R d = R.ok();
            d.put("page", page);
            d.put("fidList", dto.getFldList());

            return d;
        } catch (RRException re) {
            throw re;
        } catch (Exception e) {
            prdDataProcJobService.checkHiveException(e);
        }
        return null;
    }
    @Override
    public R comparisonData(Map<String, Object> params){
        try{
            String date1 = mstr(params, "date1");
            String date2 = mstr(params, "date2");
            Integer dataTblid = mint(params,"tabaId");
            String fldStr = mstr(params, "fldNm");
            Long tnmtid = Long.valueOf(mstr(params,"tnmtid"));
            Integer total = mint(params, "total");

            List<Map<String,String>> fldList = new ArrayList<>();
            DataTblDto dto1 = prdDataProcJobService.getHiveTable(dataTblid, PrdDataProcJobService.DB_USAGEID_CLSDB, 0, tnmtid);
            DataTblDto dto2 = prdDataProcJobService.getHiveTable(dataTblid, PrdDataProcJobService.DB_USAGEID_TMPDB, 0, tnmtid);
            dto2.setFullName(dto2.getFullName()+"_rowid");
            List<String> pkArr = prdDataProcJobService.getPrimaryKeys(dto1);
            List<DataFldEntity> pkList = prdDataProcJobService.getPrimaryFlds(dto1);
            List<String> fldArr = filterFld(fldStr, pkArr);
            int pageIndex = Integer.parseInt(params.get("page").toString());
            int pageLimit = Integer.parseInt(params.get("limit").toString());
            String countSql = buildComparisonCount(dto1, fldArr, pkArr, date1, date2); //查询总数
            String querySql = buildComparisonSql(dto1, dto2, fldArr, pkArr, date1, date2);
            Integer count = total > 0 ? total : Integer.parseInt(hiveService.selectData(countSql).get(0).get("count") + "");
            List<Map<String, Object>> list = hiveService.pageData(querySql, "t1.rowidlwq", pageIndex, pageLimit);
            PageUtils page = new PageUtils(list, count,pageLimit, count);

            R d = R.ok();
            d.put("page", page);
            d.put("fidList", buildComparisonSqlFld(dto1, fldArr, pkList));

            return d;

        }catch (RRException re) {
            throw re;
        } catch (Exception e) {
            prdDataProcJobService.checkHiveException(e);
        }
        return null;
    }

    private String buildComparisonSql(DataTblDto dto1, DataTblDto dto2, List<String> fldArr, List<String> pkArr, String date1, String date2){
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        for(String c1 : pkArr){
            sql.append("t1.").append(c1).append(",");
        }
        for(String c2 : fldArr){
            sql.append("t2.").append(c2).append(" ").append(c2).append("1,");
            sql.append("t1.").append(c2).append(" ").append(c2).append("2,");
        }
        String pkstr = StringUtils.join(pkArr, ",");
        List<String> fldUnq = filterPrimary(fldArr,pkArr);
        String fldstr1 = StringUtils.join(fldUnq, ",");
        String fldstr2 = StringUtils.join(fldArr, ",");
        String fldwhere = getTagWhere(fldArr);
        if(pkstr.length() > 0){
            pkstr += ",";
        }
        if(fldstr1.length() > 0){
            fldstr1 += ",";
        }
        if(fldstr2.length() > 0){
            fldstr2 += ",";
        }
        sql.append("t1.rowidlwq");
        sql.append(" from (select "+pkstr+fldstr1+"rowidlwq from "+dto1.getFullName()+" where data_dt>='"+date1+"' and data_dt<='"+date2+"' "+fldwhere+" ) t1");
        sql.append(" left join (select "+fldstr2+"rowidlwq from "+dto2.getFullName()+" where data_dt>='"+date1+"' and data_dt<='"+date2+"') t2 on t1.rowidlwq = t2.rowidlwq");

        return sql.toString();
    }
    private List<LinkedHashMap<String,Object>> buildComparisonSqlFld(DataTblDto dto1,List<String> fldArr, List<DataFldEntity> pkList){
        List<LinkedHashMap<String,Object>> flds = new ArrayList<>();
        LinkedHashMap<String, Object> m = new LinkedHashMap<>();
//        m.put("prop", "x.rowidlwq");
//        m.put("label", "rowid");
//        flds.add(m);

        for(DataFldEntity c1 : pkList){
            LinkedHashMap<String, Object> m1 = new LinkedHashMap<>();
            m1.put("prop", "x."+c1.getFldPhysNm());
            m1.put("label", c1.getFldCnNm());
            flds.add(m1);
        }
        for(String c2 : fldArr){
            LinkedHashMap<String, Object> m1 = new LinkedHashMap<>();
            LinkedHashMap<String, Object> m2 = new LinkedHashMap<>();
            DataFldEntity f = findFld(c2, dto1.getFldList());
            if(f == null){
                continue;
            }
            m1.put("prop", "x."+f.getFldPhysNm()+"1");
            m1.put("label", f.getFldCnNm()+"(清洗前)");
            m2.put("prop", "x."+f.getFldPhysNm()+"2");
            m2.put("label", f.getFldCnNm()+"(清洗后)");
            flds.add(m1);
            flds.add(m2);
        }
        return flds;
    }
    private String buildComparisonCount(DataTblDto dto1, List<String> fldArr, List<String> pkArr, String date1, String date2){
        String fldwhere = getTagWhere(fldArr);
        String sql = "select count(*) as count from " + dto1.getFullName() + " where data_dt>='"+date1+"' and data_dt<='" + date2 + "' " + fldwhere;
        return sql;
    }

    private DataFldEntity findFld(String name, List<DataFldEntity> list){
        for(DataFldEntity f: list){
            if(name.equals(f.getFldPhysNm()))
                return f;
        }
        return null;
    }
    private List<String> filterFld(String fldStr, List<String> pkArr){
        String[] fldArr = fldStr.split(",");
        List<String> res = new ArrayList<>();
        for(String f : fldArr){
            res.add(f);
        }
        return res;
    }
    private List<String> filterPrimary(List<String> fldAll, List<String> pkArr){
        List<String> res = new ArrayList<>();
        for(String f : fldAll){
            boolean b = false;
            for(String pk : pkArr){
                if(f.equals(pk)){
                    b = true;
                    break;
                }
            }
            if(!b){
                res.add(f);
            }
        }
        return res;
    }
    private String getTagWhere(List<String> fldArr){
        if(fldArr == null || fldArr.size() == 0)
            return " and trim(tagslwq)!= ''";
        List<String> orStr = new ArrayList<>();
        for(String f : fldArr){
            orStr.add(" tagslwq like '%#"+f+">%' ");
        }
        String fldwhere = " and (" + StringUtils.join(orStr, " or ") + ")";
        return fldwhere;
    }
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response)  throws Exception{
        String tabaId = request.getParameter("tabaId");
        String chk_projid = request.getParameter("chk_projid");
        String srcId = request.getParameter("srcId");
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tabaId",tabaId);
        params.put("id",srcId);
        params.put("date1",date1);
        params.put("date2",date2);
        List<Map<String,Object>> dataList = baseMapper.washReport(params);
        params.put("chk_projid",chk_projid);
        List<Map<String,Object>> washList = baseMapper.washInfo(params);
        Map<String,Object> washInfo = washList.size() > 0 ? washList.get(0) : new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String condition = "";//chk_projname==""?"":("清洗项目:"+chk_projname+"；\n");
        condition = condition+"数据来源："+washInfo.get("Data_Src_Nm")+"；\n"+"日期范围： 从"+
                fmtDate(date1)+" 到 "+fmtDate(date2)+"；\n"+
                "       总记录数："+getWIVal(washList,("Proc_Rec_Total_Qty"))+
                "；\n重复记录数："+getWIVal(washList,"All_Dupl_Rec_Qty")+"；     主键重复记录数："+getWIVal(washList,"Pk_Dupl_Rec_Qty")+"；";
        String name = washInfo.get("Data_Tbl_Cn_Nm")+"数据清洗报告";
        List<String> heads = new ArrayList<String>();
        heads.add("字段中文名");
        heads.add("字段英文名");
        heads.add("数据类型");
        heads.add("主键字段");
        heads.add("不可为空");
        heads.add("清洗规则");
        heads.add("清洗记录数");
        heads.add("清洗记录占比");
        heads.add("无法清洗记录数");
        heads.add("无法清洗记录占比");
        for(int i=0;i<dataList.size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("字段中文名", dataList.get(i).get("Fld_Cn_Nm"));
            map.put("字段英文名", dataList.get(i).get("Fld_Phys_Nm"));
            map.put("数据类型", dataList.get(i).get("Fld_Data_Type"));
            map.put("主键字段", dataList.get(i).get("If_Pk")!=null&&dataList.get(i).get("If_Pk").toString().equals("1")?"是":"否");
            map.put("不可为空", dataList.get(i).get("If_Can_Null")!=null&&dataList.get(i).get("If_Can_Null").toString().equals("0")?"是":"否");
            map.put("清洗规则",dataList.get(i).get("Data_Wash_Cmpu_Nm"));
            map.put("清洗记录数",dataList.get(i).get("Wash_Rec_Qty"));
            map.put("清洗记录占比",dataList.get(i).get("wash_pro")==null?"":dataList.get(i).get("wash_pro")+"%");

            map.put("无法清洗记录数",dataList.get(i).get("Retn_Rec_Qty"));
            map.put("无法清洗记录占比",dataList.get(i).get("ret_pro")==null?"":dataList.get(i).get("ret_pro")+"%");
            list.add(map);
        }
        ExportExcel.download(request, response,list,heads, name,condition);
    }
    private String fmtDate(Object obj){
        if(obj == null) return "";
        String str = obj.toString().split(" ")[0];
        return  str;
    }
    private Integer getWIVal(List<Map<String,Object>> washList, String key){
        Integer v = 0;
        for(Map<String,Object> wash : washList){
            Object o = wash.get(key);
            String s = o == null ? "0" : o.toString();
            v += Integer.valueOf(s);
        }
        return v;
    }

    @Override
    public PageUtils tableReport(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.tableReport(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public PageUtils tableReportInfo(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.tableReportInfo(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public PageUtils tableProInfo(Map<String, Object> params) {
        IPage<Map<String, Object>> page = baseMapper.tableProInfo(getPager(params), params);
        return new PageUtils(page);
    }

    @Override
    public PageUtils tableProData(Map<String, Object> params) {
        StringBuilder countSql = new StringBuilder();
        StringBuilder querySql = new StringBuilder();
        String dt = mstr(params, "date");
        String jobid= mstr(params, "jobId");
        Integer dataTblid = mint(params, "tableId");
        String where = "";


        return null;
    }

    @Override
    public String lastExecDate(Map<String, Object> params){
        return baseMapper.lastExecDate(params);
    }

    private Page getPager(Map<String, Object> params){
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        Page pager = new Page(no, limit);
        return pager;
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
}