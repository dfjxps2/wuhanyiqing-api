package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.common.utils.excel.ExportExcel;
import io.dfjinxin.modules.meta.dto.DataTblDto;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.service.DataTblService;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.service.DataWashCmpuService;
import io.dfjinxin.modules.wash.service.PrdDataProcJobService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.FldWashResultDao;
import io.dfjinxin.modules.wash.entity.FldWashResultEntity;
import io.dfjinxin.modules.wash.service.FldWashResultService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("fldWashResultService")
public class FldWashResultServiceImpl extends ServiceImpl<FldWashResultDao, FldWashResultEntity> implements FldWashResultService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PrdDataProcJobService prdDataProcJobService;
    @Autowired
    private DataWashCmpuService dataWashCmpuService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FldWashResultEntity> page = this.page(
                new Query<FldWashResultEntity>().getPage(params),
                new QueryWrapper<FldWashResultEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils washReport(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<Map<String, Object>> page = baseMapper.washReport(new Page<>(no, limit), params);
        return new PageUtils(page);
    }


    @Override
    public List<Map<String,Object>> washInfo(Map<String, Object> params) {
        return baseMapper.washInfo(params.get("jobid"));
    }

    @Override
    public List<Map<String,Object>> washProj(Map<String, Object> params) {
        return baseMapper.washProj(params.get("jobid"));
    }


    @Override
    public void download(HttpServletRequest request, HttpServletResponse response)  throws Exception{
        int jobid = Integer.parseInt(request.getParameter("jobid"));
        String chk_projid = (String) request.getParameter("chk_projid");
        String chk_projname = (String) request.getParameter("chk_projname");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("jobid",jobid);
        params.put("chk_projid",chk_projid);
        List<Map<String,Object>> dataList = baseMapper.washReport(params);
        List<Map<String,Object>> topList = baseMapper.washInfo(jobid);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String condition = "";//chk_projname==""?"":("清洗项目:"+chk_projname+"；\n");
        condition = condition+"数据来源："+topList.get(0).get("Data_Src_Nm")+"；\n"+"日期范围： 从"+
                topList.get(0).get("Data_Start_Dt")+" 到 "+topList.get(0).get("Data_Terminate_Dt")+"；\n"+
                "抽样比例："+topList.get(0).get("Data_Spl_Pct")+"%；       总记录数："+topList.get(0).get("Proc_Rec_Total_Qty")+
                "；\n重复记录数："+topList.get(0).get("All_Dupl_Rec_Qty")+"；     主鍵重复记录数："+topList.get(0).get("Pk_Dupl_Rec_Qty")+"；";
        String name = (String) topList.get(0).get("Data_Tbl_Cn_Nm");
        if(name ==null || name.equals("")){
            name  = (String) topList.get(0).get("Data_Tbl_Phys_Nm");
        }
        name = name +"数据试清洗报告";
        List<String> heads = new ArrayList<String>();
        heads.add("字段中文名");
        heads.add("字段英文名");
        heads.add("数据类型");
        heads.add("主键字段");
        heads.add("不可为空");
        heads.add("清洗项目");
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
            map.put("清洗项目", dataList.get(i).get("Data_Wash_Cmpu_Nm"));
            map.put("清洗记录数",dataList.get(i).get("Wash_Rec_Qty")!=null?dataList.get(i).get("Wash_Rec_Qty"):"");
            map.put("清洗记录占比",dataList.get(i).get("wash_pro")==null?"":dataList.get(i).get("wash_pro")+"%");

            map.put("无法清洗记录数",dataList.get(i).get("Retn_Rec_Qty")!=null?dataList.get(i).get("Retn_Rec_Qty"):"");
            map.put("无法清洗记录占比",dataList.get(i).get("ret_pro")==null?"":dataList.get(i).get("ret_pro")+"%");
            list.add(map);
        }
        ExportExcel.download(request, response,list,heads, name,condition);
    }

    @Override
    public void downloadDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception{
        int jobid = Integer.parseInt(request.getParameter("jobid"));
        Long tnmtid = Long.valueOf(request.getAttribute("Tnmtid").toString());
        Integer dataTblid = Integer.parseInt(request.getParameter("tableId"));
        int limit = PrdDataProcJobService.HIVE_EXP_LIMIT;

        DataTblDto dto = prdDataProcJobService.getHiveTable(dataTblid, PrdDataProcJobService.DB_USAGEID_ISUDB, jobid, tnmtid);
        String querySql = prdDataProcJobService.getHiveSelectSql(dto
                , "rowidlwq,tagslwq,(case when isu_type='1' or isu_type='3' then '全字段重复' when isu_type='2' then '无法清洗' when isu_type='4' then '主键重复' when isu_type='1' then '' else isu_type end) isu_type", "") + " limit " + (limit + 1);
        //只允许导出10000条，超过的不允许
        String condition = "";
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> dataList = prdDataProcJobService.selectDataRepeate(querySql);
        List<DataWashCmpuEntity> cmpuList = dataWashCmpuService.list(new QueryWrapper<DataWashCmpuEntity>().eq("Data_Wash_Cmpu_Type","0"));

        if(dataList.size() > limit){
            condition = "问题数已大于" + limit + "行，本表格只提供前"+limit+"行，需要完整记录请联系管理员，由后台作导出。";
            dataList.remove(dataList.size() - 1);
        }
        String name = dto.getTbl().getDataTblCnNm();
        if(name == null || name.equals("") ){
          name  = dto.getTbl().getDataTblPhysNm();
        }
        name = name + "问题数据";
        List<String> heads = new ArrayList<String>();
        for(DataFldEntity f : dto.getFldList()){
            String fnm = StringUtils.isNotBlank(f.getFldCnNm()) ? f.getFldCnNm() : f.getFldPhysNm();
            heads.add(fnm);
        }
        heads.add("问题类型");
        heads.add("问题描述");
        list = downloadDetailConvert(dataList, dto, cmpuList);
        ExportExcel.download(request, response,list,heads, name,condition);
    }

    private List<Map<String, Object>> downloadDetailConvert(List<Map<String, Object>> dataList, DataTblDto dto,List<DataWashCmpuEntity> cmpuList){
        List<Map<String, Object>> list = new ArrayList<>();
        for(Map<String, Object> m : dataList){
            Map<String, Object> map = new LinkedHashMap<>();

            for(DataFldEntity f : dto.getFldList()){
                String fnm = StringUtils.isNotBlank(f.getFldCnNm()) ? f.getFldCnNm() : f.getFldPhysNm();
                map.put(fnm, m.get( f.getFldPhysNm() ));
            }
            map.put("问题类型",m.get("isu_type"));
            map.put("问题描述",prdDataProcJobService.tagConvert(m, m.get("tagslwq").toString(), dto.getFldList(), cmpuList));

            list.add(map);
        }
        return list;
    }
}