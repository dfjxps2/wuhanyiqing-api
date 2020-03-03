package io.dfjinxin.modules.tag.controller;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.service.DataPartService;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.storage.service.SelectedTagService;
import io.dfjinxin.modules.storage.tree.EntityTree;
import io.dfjinxin.modules.storage.tree.TreeUtil;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.tag.service.TagDataTblService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 字段标签管理-标签视图
 * @Author: z.h.c
 * @CreateDate: 2019/6/27 11:25
 * @Version: 1.0
 */

@RestController
@RequestMapping("/tag/field")
@Api("字段标签管理-标签视图")
public class FiledLabelViewController extends AbstractController {

    @Autowired
    private SelectedTagService selectedTagService;

    @Autowired
    private DataPartService dataPartService;

    @Autowired
    private TagDataTblService tagDataTblService;

    /**
     * 字段标签管理-标签视图-左侧标签列表
     * 根据分区、标签所属表、字段查询指定标签被使用的次数
     *
     * @param params
     * @return
     */
    @ApiOperation("段标签管理-标签视图-左侧标签查询")
    @PostMapping(value = "/getFieldViewTreeData")
    public R getTableListByPage(@RequestBody Map<String, Object> params) {

        if (params.isEmpty()) {
            R.error("请求参数为空!");
        }

        //标签的目录id
        ArrayList<Object> catgyList = (ArrayList) params.get("catgyList");
        //表标签所项：标签所在目录id，只能选择目录，不能选择标签
        ArrayList<Object> leafList = (ArrayList) params.get("leafList");
        //字段标签id
        ArrayList<Object> fldLabelList = (ArrayList) params.get("fldLabelList");
        Integer partId = (Integer) params.get("partId");//分区id
        Integer dbId = (Integer) params.get("dbId");//数据库id
        Long tnmtid = this.getTenantId();//租户id
        if (partId != null) {
            DataPartEntity dataPartEntity = dataPartService.getById(partId);//分区id查询出租户id
            Integer tnmtIdTemp = dataPartEntity.getTnmtid();
            if (tnmtIdTemp == tnmtid.intValue()) {
                List<EntityTree> list = selectedTagService.getFieldViewTreeData(catgyList, leafList, fldLabelList, partId, dbId, tnmtid);//返回的最终集合，可根据自己返回类型更改。
                List<EntityTree> tree = TreeUtil.getTreeList("", list);//调用工具类，第一个参数是默认传入的顶级id，和查询出来的数据
                return R.ok().put("page", tree);
            } else {
                return R.error( "该租户的分区不正确");
            }
        } else {
            return R.error( "请传入分区的值");
        }
    }

    /**
     * 字段标签管理-标签视图-右侧数据表格
     * 根据标签id分页查询已使用该标签的字段及字段所在表
     * 分页
     *
     * @param desenForm
     * @return
     */
    @ApiOperation("字段标签管理-标签视图-右侧数据表格查询")
    @PostMapping("/queryLabelViewInfoByPage")
    public R queryLabelViewInfoByPage(@RequestBody DesenForm desenForm) {

        if (desenForm == null) {
            R.error("请求参数为空!");
        }
        desenForm.markToInt();
        PageUtils page = tagDataTblService.queryLabelViewInfoByPage(desenForm, this.getTenantId().intValue());
        return R.ok().put("data", page);
    }
}
