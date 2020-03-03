package io.dfjinxin.modules.wash.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.entity.DataExplTmplProjEntity;
import io.dfjinxin.modules.wash.entity.DataFldExplProjEntity;
import io.dfjinxin.modules.wash.service.DataChkProjService;
import io.dfjinxin.modules.wash.service.DataExplTmplProjService;
import io.dfjinxin.modules.wash.service.DataFldExplProjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.wash.entity.DataExplTmplEntity;
import io.dfjinxin.modules.wash.service.DataExplTmplService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据探查模板
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@RestController
@RequestMapping("wash/dataexpltmpl")
public class DataExplTmplController  extends AbstractController {
    @Autowired
    private DataExplTmplService dataExplTmplService;
    @Autowired
    private DataExplTmplProjService dataExplTmplProjService;
    @Autowired
    private DataFldExplProjService dataFldExplProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:dataexpltmpl:list")
    public R list(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        PageUtils page = dataExplTmplService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 查询模板
     */
    @RequestMapping("/selectById")
    @RequiresPermissions("wash:dataexpltmpl:selectById")
    public R selectByExplId(@RequestParam Map<String, Object> params){
        long tnmtid = getTenantId();
        params.put("tnmtid",tnmtid);
        PageUtils page = dataExplTmplService.selectByExplId(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据模板id查询模板
     */
    @RequestMapping("/getExplById")
    @RequiresPermissions("wash:dataexpltmpl:getExplById")
    public R getExplById(@RequestParam Map<String, Object> params){
        long tnmtid = getTenantId();
        params.put("tnmtid",tnmtid);
        List<Map<String,Object>> page = dataExplTmplService.getExplById( params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataExplTmplid}")
    @RequiresPermissions("wash:dataexpltmpl:info")
    public R info(@PathVariable("dataExplTmplid") Integer dataExplTmplid){
        DataExplTmplEntity dataExplTmpl = dataExplTmplService.getById(dataExplTmplid);

        return R.ok().put("dataExplTmpl", dataExplTmpl);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:dataexpltmpl:save")
    public R save(@RequestBody DataExplTmplEntity dataExplTmpl){
        dataExplTmplService.save(dataExplTmpl);
        return R.ok();
    }

    /**
     * 添加模板
     */
    @RequestMapping("/add")
    @RequiresPermissions("wash:dataexpltmpl:add")
    public R save(@RequestBody JSONObject params){
        long tnmtid = getTenantId();
        params.put("tnmtid",tnmtid);
        int cnt = existsNm(params.get("name"), 0, tnmtid);
        if(cnt > 0){
            return R.error("您所输入的数据探查模板名称已存在，请重新修改");
        }
        dataExplTmplService.add(params);
        return R.ok();
    }

    private int existsNm(Object name, Object id, long tnmtid){
        int cnt = dataExplTmplService.count(
                new QueryWrapper<DataExplTmplEntity>()
                        .eq("Data_Expl_Tmpl_Nm", name)
                        .eq("Tnmtid", tnmtid)
                        .ne("Data_Expl_Tmplid", id)
        );
        return cnt;
    }

    //批量修改
    @RequestMapping("/updatePojo")
    @RequiresPermissions("wash:dataexpltmpl:updatePojo")
    public R updatePojo(@RequestBody JSONObject params){
        dataExplTmplService.updatePojo(params);
        return R.ok();
    }

    //删除模板
    @RequestMapping("/deleteById")
    @RequiresPermissions("wash:dataexpltmpl:deleteById")
    public R deleteById(@RequestBody JSONObject params){
        List<Integer> list = (List<Integer>) params.get("id");
        int cnt = dataFldExplProjService.count(new QueryWrapper<DataFldExplProjEntity>().in("Data_Expl_Tmplid", list));
        if(cnt>0){
            return R.error("所要删除的记录正在使用中，不允许删除");
        }
        dataExplTmplProjService.deleteByExp(list.toArray(new Integer[0]));
        dataExplTmplService.removeByIds(list);
        return R.ok();
    }

    //根据id修改
    @RequestMapping("/updateById")
    @RequiresPermissions("wash:dataexpltmpl:updateById")
    public R updateById(@RequestBody JSONObject params){
        long tnmtid = getTenantId();
        List<Integer> list = (List<Integer>) params.get("id");
        int cnt = existsNm(params.get("name"), list.get(0), tnmtid);
        if(cnt > 0){
            return R.error("您所输入的数据探查模板名称已存在，请重新修改");
        }
        dataExplTmplService.updateById(params);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:dataexpltmpl:update")
    public R update(@RequestBody DataExplTmplEntity dataExplTmpl){
        dataExplTmplService.updateById(dataExplTmpl);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:dataexpltmpl:delete")
    public R delete(@RequestBody Integer[] dataExplTmplids){
        dataExplTmplService.removeByIds(Arrays.asList(dataExplTmplids));

        return R.ok();
    }
}
