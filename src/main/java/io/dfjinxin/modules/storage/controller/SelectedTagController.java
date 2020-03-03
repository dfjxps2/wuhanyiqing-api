package io.dfjinxin.modules.storage.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.service.DataPartService;
import io.dfjinxin.modules.storage.entity.SelectedTagEntity;
import io.dfjinxin.modules.storage.service.SelectedTagService;
import io.dfjinxin.modules.storage.tree.EntityTree;
import io.dfjinxin.modules.storage.tree.TreeUtil;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 14:26:46
 */
@RestController
@RequestMapping("storage/selectedTag")
public class SelectedTagController extends AbstractController {
    @Autowired
    private SelectedTagService selectedTagService;

    @Autowired
    private DataPartService dataPartService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:taginfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = selectedTagService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取树形下拉框的数据
     * vue 的select-tree 支持显示节点下的叶子数量， el-tree 不支持显示数量
     * type:["el-tree":el-tree的数据 ，
     *       "select-tree":select-tree 的数据
     *       "el-tree-show-count":查询el-tree的数据 并获取标签下数量]
     */
    @RequestMapping("/getTreeData")
    public R getTreeData(@RequestBody Map<String, Object> params){
        Integer partId=(Integer)params.get("partId");//获取分区id
        long sysTnmtid=this.getTenantId();//租户id
        if(partId!=null){
            DataPartEntity dataPartEntity = dataPartService.getById(partId);//分区id查询出租户id
            Integer queryTnmtId =dataPartEntity.getTnmtid();
            if(queryTnmtId-sysTnmtid==0){
                params.put("tnmtId",sysTnmtid);
                List<EntityTree> list = selectedTagService.getTreeData(params);//返回的最终集合，可根据自己返回类型更改。
                List<EntityTree> tree = TreeUtil.getTreeList("", list);//调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
                TreeUtil.resultNum(tree);
                return R.ok().put("page", tree);
            }else{
                return R.error(500,"租户的分区数据异常，无法执行相关查询！");
            }
        }else{
            return R.error(500,"未获取到分区数据，无法执行相关查询！");
        }
    }

    /**
     * 获取树形下拉框的数据
     */
    @RequestMapping("/getSelectTreeData")
    public R getSelectTreeData(@RequestBody Map<String, Object> params){
        long sysTnmtid=this.getTenantId();//租户id
        params.put("tnmtId",sysTnmtid);
        List<EntityTree> list = selectedTagService.getSelectTreeData(params);//返回的最终集合，可根据自己返回类型更改。
        List<EntityTree> tree = TreeUtil.getTreeList("", list);//调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
        return R.ok().put("page", tree);
    }

    /**
     * 通过叶子标签id获取 表数量/表标签数量/记录数/字段数/字段标签数
     */
    @RequestMapping("/getDetailCount")
    public R getDetailCount(@RequestBody Map<String, Object> params){
        ArrayList<Object> leafList=(ArrayList)params.get("leafList");
        Integer partId=(Integer)params.get("partId");//获取分区id
        String dbId=params.get("dbId")+"";//获取分区id
        long sysTnmtid=this.getTenantId();//租户id
        if(partId!=null){
            DataPartEntity dataPartEntity = dataPartService.getById(partId);//分区id查询出租户id
            Integer queryTnmtId =dataPartEntity.getTnmtid();
            if(queryTnmtId-sysTnmtid==0){
                List<Map<String,String>> detailInfoList = selectedTagService.getDetailCount(leafList,partId,dbId,sysTnmtid);
                return R.ok().put("list", detailInfoList);
            }else{
                return R.error(500,"租户的分区数据异常，无法执行相关查询！");
            }
        }else{
            return R.error(500,"未获取到分区数据，无法执行相关查询！");
        }


    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:taginfo:info")
    public R info(@PathVariable("id") Integer id){
		SelectedTagEntity tagInfo = selectedTagService.getById(id);
        return R.ok().put("tagInfo", tagInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:taginfo:save")
    public R save(@RequestBody SelectedTagEntity tagInfo){
		selectedTagService.save(tagInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:taginfo:update")
    public R update(@RequestBody SelectedTagEntity tagInfo){
		selectedTagService.updateById(tagInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:taginfo:delete")
    public R delete(@RequestBody Integer[] ids){
		selectedTagService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
