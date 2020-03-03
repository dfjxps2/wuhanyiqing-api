package io.dfjinxin.modules.tag.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.service.DataFldMarkIdxService;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.tag.service.TagDataTblService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 字段标签管理 controller
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2019-06-16 15:18:37
 */
@RestController
@RequestMapping("/tag/datatbl")
@Api(value = "字段标签管理")
public class TagDataTblController extends AbstractController {

    @Autowired
    private TagDataTblService dataTblService;

    @Autowired
    private DataFldMarkIdxService dataFldMarkIdxService;

    /**
     * 根据数据分区、数据源、表名、表标签分页查询数据表
     *
     * @param desenForm
     * @return
     */
    @PostMapping("/getTableListByPage")
    @ApiOperation(value = "字段标签管理-分页查询数据表")
    public R getTableListByPage(@RequestBody DesenForm desenForm) {

        if (desenForm == null) {
            R.error("请求参数为空");
        }
        desenForm.markToInt();
        PageUtils pageUtils = dataTblService.queryByCriteria(desenForm, this.getTenantId());

        return R.ok().put("data", pageUtils);
    }


    /**
     * 分页查询
     * 根据表id，查询表信息、表字段信息、字段的标签
     *
     * @return
     */
    @RequestMapping("/getFldsAndLabelsByTblIds")
    public R getTblAndFldInfoListbyTblIds(@RequestBody DesenForm desenForm) {

        if (desenForm == null) {
            R.error("请求参数为空!");
        }

        desenForm.markToInt();
        PageUtils page = dataTblService.getTblAndFldInfoListbyTblIds(desenForm,this.getTenantId().intValue());

        return R.ok().put("data", page);
    }


    /**
     * 批量添加字段标签
     *
     * @return
     */
    @RequestMapping("/batchSaveFldLabel")
    public R batchSave(@RequestBody Map<String, Object> map) {
        if (map.isEmpty()) {
            return R.error("字段列表或标签列表为空");
        }
        List<Integer> fldList = (List<Integer>) map.get("fldList");
        List<Integer> labelList = (List<Integer>) map.get("labelList");
        dataFldMarkIdxService.batchAddFldLabel(fldList, labelList);
        return R.ok();
    }

    /**
     * 批量删除字段标签
     */
    @RequestMapping("/batchDelFldLabel")
    @RequiresPermissions("meta:datafldmarkidx:delete")
    public R batchDelete(@RequestBody Map<String, Object> map) {
        if (map.isEmpty()) {
            R.error("字段id或标签id为空!");
        }
        List<Integer> fldList = (List<Integer>) map.get("fldList");
        List<Integer> labelList = (List<Integer>) map.get("labelList");
        dataFldMarkIdxService.batchDelFldLabel(fldList, labelList);

        return R.ok();
    }

}
