package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.common.utils.excel.ExportExcel;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.FldExplResultDao;
import io.dfjinxin.modules.wash.entity.FldExplResultEntity;
import io.dfjinxin.modules.wash.service.FldExplResultService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service("fldExplResultService")
public class FldExplResultServiceImpl extends ServiceImpl<FldExplResultDao, FldExplResultEntity> implements FldExplResultService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FldExplResultEntity> page = this.page(
                new Query<FldExplResultEntity>().getPage(params),
                new QueryWrapper<FldExplResultEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils expReport(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<Map<String, Object>> page = baseMapper.expReport(new Page<>(no, limit), params);
        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> expInfo(Map<String, Object> params) {
        return baseMapper.expInfo(params.get("jobid"));
    }

    @Override
    public List<Map<String,Object>> expProj(Map<String, Object> params) {
        return baseMapper.expProj(params.get("jobid"));
    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response)  throws Exception{
        int jobid = Integer.parseInt(request.getParameter("jobid"));
        String chk_projid = (String) request.getParameter("chk_projid");
        String chk_projname = (String) request.getParameter("chk_projname");
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("jobid",jobid);
        params.put("chk_projid",chk_projid);
        List<Map<String,Object>> dataList = baseMapper.expReport(params);
        List<Map<String,Object>> topList = baseMapper.expInfo(jobid);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String condition = chk_projname==""?"":("探查项目:"+chk_projname+"；\n");
        condition = condition+"数据来源："+topList.get(0).get("Data_Src_Nm")+"；\n"+"日期范围： 从"+
                topList.get(0).get("Data_Start_Dt")+" 到 "+topList.get(0).get("Data_Terminate_Dt")+"；\n"+
                "抽样比例："+topList.get(0).get("Data_Spl_Pct")+"%；       总记录数："+topList.get(0).get("Proc_Rec_Total_Qty")+
                "；\n重复记录数："+topList.get(0).get("All_Dupl_Rec_Qty")+"；     主键重复记录数："+topList.get(0).get("Pk_Dupl_Rec_Qty")+"；";
        String name = (String) topList.get(0).get("Data_Tbl_Cn_Nm");
        if(name ==null || name.equals("")){
            name = (String) topList.get(0).get("Data_Tbl_Phys_Nm");
        }
        name = name +"数据探查报告";
        List<String> heads = new ArrayList<String>();
        heads.add("字段中文名");
        heads.add("字段英文名");
        heads.add("数据类型");
        heads.add("主键字段");
        heads.add("不可为空");
        heads.add("探查项目");
        heads.add("问题记录数");
        heads.add("问题记录占比");
        for(int i=0;i<dataList.size();i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("字段中文名", dataList.get(i).get("Fld_Cn_Nm"));
            map.put("字段英文名", dataList.get(i).get("Fld_Phys_Nm"));
            map.put("数据类型", dataList.get(i).get("Fld_Data_Type"));
            map.put("主键字段", dataList.get(i).get("If_Pk")!=null&&dataList.get(i).get("If_Pk").toString().equals("1")?"是":"否");
            map.put("不可为空", dataList.get(i).get("If_Can_Null")!=null&&dataList.get(i).get("If_Can_Null").toString().equals("0")?"是":"否");
            map.put("探查项目", dataList.get(i).get("Chk_Proj_Nm"));
            map.put("问题记录数",dataList.get(i).get("Isu_Rec_Qty"));//!=null&&(Integer) dataList.get(i).get("Isu_Rec_Qty")>0?dataList.get(i).get("Isu_Rec_Qty"):""
            if(dataList.get(i).get("Chk_Proj_Nm")!=null){
                map.put("问题记录占比",dataList.get(i).get("Exc_Pro").equals("0")?"":dataList.get(i).get("Exc_Pro")+"%");
            }else{
                map.put("问题记录占比","");
            }

            list.add(map);
        }
        ExportExcel.download(request, response,list,heads, name,condition);
    }

}