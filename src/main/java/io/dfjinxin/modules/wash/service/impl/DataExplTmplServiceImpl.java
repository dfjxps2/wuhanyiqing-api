package io.dfjinxin.modules.wash.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.DataExplTmplProjEntity;
import io.dfjinxin.modules.wash.service.DataExplTmplProjService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.wash.dao.DataExplTmplDao;
import io.dfjinxin.modules.wash.entity.DataExplTmplEntity;
import io.dfjinxin.modules.wash.service.DataExplTmplService;


@Service("dataExplTmplService")
public class DataExplTmplServiceImpl extends ServiceImpl<DataExplTmplDao, DataExplTmplEntity> implements DataExplTmplService {

    @Autowired
    private DataExplTmplProjService dataExplTmplProjService;

    @Autowired
    private DataExplTmplService dataExplTmplService;


    @Override
    public int add(Map<String, Object> params) {
        DataExplTmplEntity d = new DataExplTmplEntity();
        d.setDataExplTmplNm((String) params.get("name"));
        d.setDataExplTmplDesc((String) params.get("info"));
        d.setTmplType(1);
        d.setTnmtid(Integer.parseInt(String.valueOf(params.get("tnmtid"))));
        dataExplTmplService.save(d);
        List<Integer> l = (List<Integer>) params.get("pojo");
        int a = 1;
        for(Integer attribute : l) {
            a++;
            DataExplTmplProjEntity dp = new DataExplTmplProjEntity();
            dp.setDataExplTmplid(d.getDataExplTmplid());
            dp.setChkProjid(attribute);
            dp.setExctOrd(a);
            dataExplTmplProjService.save(dp);
        }
        return a;
    }

    @Override
    public int updatePojo(Map<String, Object> params) {
        List<Map<String,Object>> l =(List<Map<String,Object>>) params.get("prjo");
        List<Integer> li = new ArrayList();
        for(Map<String,Object> mu : l) {
            li.add((Integer) mu.get("Data_Expl_Tmplid"));
        }
        dataExplTmplProjService.deleteByExp(li.toArray(new Integer[0]));
        for(Map<String,Object> att : l) {
            List<Integer> sd = (List<Integer>) att.get("prList");
            int id = (Integer) att.get("Data_Expl_Tmplid");
            int a = 1;
            for(Integer mp : sd) {
                a++;
                DataExplTmplProjEntity dp = new DataExplTmplProjEntity();
                dp.setDataExplTmplid(id);
                dp.setChkProjid(mp);
                dp.setExctOrd(a);
                dataExplTmplProjService.save(dp);
            }
        }
        return 1;
    }

    @Override
    public int updateById(Map<String, Object> params) {
        dataExplTmplProjService.deleteByExp(((List<Integer>) params.get("id")).toArray(new Integer[0]));
        DataExplTmplEntity de = new  DataExplTmplEntity();
        de.setDataExplTmplid(((List<Integer>) params.get("id")).get(0));
        de.setDataExplTmplNm((String)params.get("name"));
        de.setDataExplTmplDesc((String)params.get("info"));
        dataExplTmplService.updateById(de);
        int a = 0;
        for(Integer mp : (List<Integer>) params.get("pojo")) {
            a++;
            DataExplTmplProjEntity dp = new DataExplTmplProjEntity();
            dp.setDataExplTmplid((((List<Integer>) params.get("id"))).get(0));
            dp.setChkProjid(mp);
            dp.setExctOrd(a);
            dataExplTmplProjService.save(dp);
        }
        return a;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        String tenantId = params.containsKey("tenantId") ? params.get("tenantId").toString() : "0";
        IPage<DataExplTmplEntity> page = this.page(
                new Query<DataExplTmplEntity>().getPage(params),
                new QueryWrapper<DataExplTmplEntity>().like(StringUtils.isNotBlank(key),"Data_Expl_Tmpl_Nm", key).eq("Tnmtid", tenantId)
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils selectByExplId(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        IPage<Map<String, Object>> page = baseMapper.selectByExplId(new Page<>(no, limit), params);
        List<Map<String,Object>> mouldList = page.getRecords();
        List<Map<String,Object>> projList = baseMapper.queryProj();
        for(Map<String,Object> mu : mouldList) {
            List<Integer> list = new LinkedList<Integer>();
            for(Map<String,Object> pr : projList) {
                String prTmplid = pr.get("Data_Expl_Tmplid") == null ? "" : pr.get("Data_Expl_Tmplid").toString();
                if(mu.get("Data_Expl_Tmplid").toString().equals(prTmplid)){
                    list.add((Integer) pr.get("Chk_Projid"));
                }
            }
            mu.put("prList",list);
        }
        page.setRecords(mouldList);
        return new PageUtils(page);
    }

    @Override
    public List<Map<String,Object>> getExplById(Map<String, Object> params) {
        List<Map<String,Object>> list = baseMapper.getExplById(params);
        return list;
    }

}