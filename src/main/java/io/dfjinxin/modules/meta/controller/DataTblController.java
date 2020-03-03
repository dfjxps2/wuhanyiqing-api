package io.dfjinxin.modules.meta.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.service.DataPartService;
import io.dfjinxin.modules.meta.service.DataTblService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 数据表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@RestController
@RequestMapping("meta/datatbl")
public class DataTblController extends AbstractController {
    @Autowired
    private DataTblService dataTblService;

    @Autowired
    private DataPartService dataPartService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:datatbl:list")
    public R list(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        PageUtils page = dataTblService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataTblid}")
    @RequiresPermissions("meta:datatbl:info")
    public R info(@PathVariable("dataTblid") Integer dataTblid){
		DataTblEntity dataTbl = dataTblService.getById(dataTblid);

        return R.ok().put("dataTbl", dataTbl);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:datatbl:save")
    public R save(@RequestBody DataTblEntity dataTbl){
		dataTblService.save(dataTbl);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:datatbl:update")
    public R update(@RequestBody DataTblEntity dataTbl){
		dataTblService.updateById(dataTbl);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:datatbl:delete")
    public R delete(@RequestBody Integer[] dataTblids){
		dataTblService.removeByIds(Arrays.asList(dataTblids));

        return R.ok();
    }

    /**
     * 通过【数据分区，表中文名称】获取列表
     */
    @RequestMapping("/listByParams")
    @RequiresPermissions("meta:datatbl:list")
    public R listByParams(@RequestBody Map<String, Object> params){
        Integer partId=(Integer)params.get("partId");//获取分区id
        if(partId!=null){
            DataPartEntity dataPartEntity = dataPartService.getById(partId);//分区id查询出租户id
            Integer queryTnmtId =dataPartEntity.getTnmtid();
            if(queryTnmtId-this.getTenantId()==0){
                PageUtils page = dataTblService.listByParams(params);
                return R.ok().put("page", page);
            }else{
                return R.error(500,"租户的分区数据异常，无法执行相关查询！");
            }
        }else{
            return R.error(500,"未获取到分区数据，无法执行相关查询！");
        }
    }

    /**
     * 通过【数据分区，表中文名称】获取统计数据
     */
    @RequestMapping("/getCount")
    @RequiresPermissions("meta:datatbl:list")
    public R getCount(@RequestBody Map<String, Object> params){
        Integer partId=(Integer)params.get("partId");//获取分区id
        if(partId!=null){
            DataPartEntity dataPartEntity = dataPartService.getById(partId);//分区id查询出租户id
            Integer queryTnmtId =dataPartEntity.getTnmtid();
            if(queryTnmtId-this.getTenantId()==0){
                params.put("tnmtId",queryTnmtId);
                List<Map<String,String>> list = dataTblService.getCount(params);
                return R.ok().put("list", list);
            }else{
                return R.error(500,"租户的分区数据异常，无法执行相关查询！");
            }
        }else{
            return R.error(500,"未获取到分区数据，无法执行相关查询！");
        }
    }

    /**
     * type=1 通过【Data_Tblid】获取表标签列表
     * type=2 通过【Data_Tblid】获取【data_fld连接data_fld_mark_idx 连接label】字段标签列表
     * type=3 通过【Data_Tblid】获取表的记录数
     * 获取的是分组数据 通过group by 统计分组的总数
     */
    @RequestMapping("/getTableTagList")
    @RequiresPermissions("meta:datatbl:list")
    public R getTagDataList(@RequestBody Map<String, Object> params){
        Integer flag=(Integer) params.get("isJoinLabel");
        Integer type=(Integer) params.get("type");
        Integer partId=(Integer) params.get("partId");
        String  tableName=(String) params.get("tableName");
        String  dbId=params.get("dbId")+"";
        ArrayList<Object> labelIdList=(ArrayList)params.get("labelList");
        long tnmtId=this.getTenantId();
        List<Map<String,String>> page =null;
        if(flag==0){
            page = dataTblService.getTagDataList(type,partId,dbId,tableName,tnmtId);
        }else{
            page = dataTblService.getTagDataListJoinLabel(type,partId,dbId,labelIdList,tnmtId);
        }
        return R.ok().put("page", page);
    }


    /**
     * type=1 通过【Data_Tblid】获取表标签列表
     * type=2 通过【Data_Tblid】获取【data_fld连接data_fld_mark_idx 连接label】字段标签列表
     * type=3 通过【Data_Tblid】获取表的记录数
     * 获取的是未被分组的详细数据
     */
    @RequestMapping("/getDetailByTableRow")
    @RequiresPermissions("meta:datatbl:list")
    public R getDetailByTableRow(@RequestBody Map<String, Object> params){
        /*Integer type=(Integer) params.get("type");
        Integer clickLabelId=(Integer) params.get("labelId");
        Integer partId=(Integer) params.get("partId");
        String dbId=params.get("dbId")+"";
        String tableName=(String) params.get("tableName");*/
        params.put("tnmtId",this.getTenantId());
        PageUtils page =dataTblService.getDetailByTableRow(params);
        return R.ok().put("page", page);
    }
}
