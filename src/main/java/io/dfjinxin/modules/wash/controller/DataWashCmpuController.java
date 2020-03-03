package io.dfjinxin.modules.wash.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.service.DataWashCmpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;



/**
 * 数据清洗运算
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@RestController
@RequestMapping("wash/datawashcmpu")
public class DataWashCmpuController extends AbstractController {
    @Autowired
    private DataWashCmpuService dataWashCmpuService;



    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:datawashcmpu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataWashCmpuService.queryPage(params);

        return R.ok().put("page", page);
    }

    @RequestMapping("/queryBywashType")
    public R queryBywashType(Integer washType){
        List<DataWashCmpuEntity> list = dataWashCmpuService.queryDataWashCmpuByDataWashCmpuType(washType);
        for(DataWashCmpuEntity e : list){
            e.valWashCmpuPara();
        }
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{dataWashCmpuid}")
    @RequiresPermissions("wash:datawashcmpu:info")
    public R info(@PathVariable("dataWashCmpuid") Integer dataWashCmpuid){
		DataWashCmpuEntity dataWashCmpu = dataWashCmpuService.getById(dataWashCmpuid);
        return R.ok().put("dataWashCmpu", dataWashCmpu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:datawashcmpu:save")
    public R save(@RequestBody DataWashCmpuEntity dataWashCmpu) throws IOException, InterruptedException {
        dataWashCmpu.setTnmtid(this.getTenantId());
        dataWashCmpuService.saveInfo(dataWashCmpu);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:datawashcmpu:update")
    public R update(@RequestBody DataWashCmpuEntity dataWashCmpu) throws IOException, InterruptedException {
        dataWashCmpu.setTnmtid(this.getTenantId());
        dataWashCmpuService.updateInfo(dataWashCmpu);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:datawashcmpu:delete")
    public R delete(@RequestBody Integer[] dataWashCmpuids){
		//dataWashCmpuService.removeByIds(Arrays.asList(dataWashCmpuids));
        return R.ok();
    }
    @RequestMapping("/queryList")
    @RequiresPermissions("wash:datawashcmpu:list")
    public R queryList(@RequestParam Map<String, Object> params){
        PageUtils page = dataWashCmpuService.queryList(params);

        return R.ok().put("page", page);
    }
}
