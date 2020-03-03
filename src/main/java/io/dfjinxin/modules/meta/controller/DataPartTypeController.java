package io.dfjinxin.modules.meta.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataPartTypeEntity;
import io.dfjinxin.modules.meta.service.DataPartTypeService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 数据分区类型
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@RestController
@RequestMapping("meta/dataparttype")
public class DataPartTypeController extends AbstractController {

    @Autowired
    private DataPartTypeService dataPartTypeService;

    /**
     * 根据租户id查询所属该租户的分区类型列表
     * add by zhc
     */
    @RequestMapping("/getList")
    public R getList(@RequestParam Map<String, Object> params) {
        List list = dataPartTypeService.queryDataPartList(this.getTenantId());
        return R.ok().put("data", list);

    }


    /**
     * 信息
     */
    @RequestMapping("/info/{partTypeid}")
    @RequiresPermissions("meta:dataparttype:info")
    public R info(@PathVariable("partTypeid") String partTypeid) {
        DataPartTypeEntity dataPartType = dataPartTypeService.getById(partTypeid);

        return R.ok().put("dataPartType", dataPartType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:dataparttype:save")
    public R save(@RequestBody DataPartTypeEntity dataPartType) {
        dataPartTypeService.save(dataPartType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:dataparttype:update")
    public R update(@RequestBody DataPartTypeEntity dataPartType) {
        dataPartTypeService.updateById(dataPartType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:dataparttype:delete")
    public R delete(@RequestBody String[] partTypeids) {
        dataPartTypeService.removeByIds(Arrays.asList(partTypeids));

        return R.ok();
    }

}
