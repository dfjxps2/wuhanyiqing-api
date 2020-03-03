package io.dfjinxin.modules.meta.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.UserContenUtils;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.meta.entity.DataTblMarkIdxEntity;
import io.dfjinxin.modules.meta.service.DataTblMarkIdxService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;


/**
 * 数据表标引
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@RestController
@RequestMapping("meta/datatblmarkidx")
public class DataTblMarkIdxController extends AbstractController {
    @Autowired
    private DataTblMarkIdxService dataTblMarkIdxService;

    /**
     * 列表
     */
    @RequestMapping("/queryDataTblMarkIdx")
    @RequiresPermissions("meta:datatblmarkidx:list")
    public R list(@RequestParam Map<String, Object> params) {
        //为在表标签管理中添加租户条件而写
        params.put("tnmtid", this.getTenantId());
        PageUtils page = dataTblMarkIdxService.queryDataTblMarkIdx(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataTblid}")
    @RequiresPermissions("meta:datatblmarkidx:info")
    public R info(@PathVariable("dataTblid") Integer dataTblid) {
        DataTblMarkIdxEntity dataTblMarkIdx = dataTblMarkIdxService.getById(dataTblid);

        return R.ok().put("dataTblMarkIdx", dataTblMarkIdx);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:datatblmarkidx:save")
    public R save(@RequestBody DataTblMarkIdxEntity dataTblMarkIdx) {
        dataTblMarkIdxService.save(dataTblMarkIdx);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:datatblmarkidx:update")
    public R update(@RequestBody DataTblMarkIdxEntity dataTblMarkIdx) {
        dataTblMarkIdxService.updateById(dataTblMarkIdx);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:datatblmarkidx:delete")
    public R delete(@RequestBody Integer[] dataTblids) {
        dataTblMarkIdxService.removeByIds(Arrays.asList(dataTblids));

        return R.ok();
    }


    /**
     * 批量添加表标签
     *
     * @return
     */
    @RequestMapping("/batchSaveTblLabel")
    public R batchSaveTblLabel(@RequestBody Map<String, Object> map) {
        List<Integer> tblList = (List<Integer>) map.get("tblList");
        List<Integer> labelList = (List<Integer>) map.get("labelList");
        dataTblMarkIdxService.batchSaveTblLabel(tblList, labelList);
        return R.ok();
    }


    /**
     * 批量删除表标签
     *
     * @param map
     * @return
     */
    @RequestMapping("/batchDeleteTblLabel")
    @RequiresPermissions("meta:datafldmarkidx:delete")
    public R batchDeleteTblLabel(@RequestBody Map<String, Object> map) {
        List<Integer> tblList = (List<Integer>) map.get("tblList");
        List<Integer> labelList = (List<Integer>) map.get("labelList");
        dataTblMarkIdxService.batchDeleteTblLabel(tblList, labelList);
        return R.ok();
    }

}
