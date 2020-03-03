package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.wash.entity.DataTblWashProjEntity;
import io.dfjinxin.modules.wash.service.DataTblWashProjService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据表清洗项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@RestController
@RequestMapping("wash/datatblwashproj")
public class DataTblWashProjController extends AbstractController {
    @Autowired
    private DataTblWashProjService dataTblWashProjService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:datatblwashproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataTblWashProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{dataTblid}")
    @RequiresPermissions("wash:datatblwashproj:info")
    public R info(@PathVariable("dataTblid") Integer dataTblid){
		DataTblWashProjEntity dataTblWashProj = dataTblWashProjService.getById(dataTblid);

        return R.ok().put("dataTblWashProj", dataTblWashProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:datatblwashproj:save")
    public R save(@RequestBody DataTblWashProjEntity dataTblWashProj){
		dataTblWashProjService.save(dataTblWashProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:datatblwashproj:update")
    public R update(@RequestBody DataTblWashProjEntity dataTblWashProj){
		dataTblWashProjService.updateById(dataTblWashProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:datatblwashproj:delete")
    public R delete(@RequestBody Integer[] dataTblids){
		dataTblWashProjService.removeByIds(Arrays.asList(dataTblids));

        return R.ok();
    }

    @RequestMapping("/ruleList")
    @RequiresPermissions("wash.rule.ruleList")
    public R ruleList(@RequestParam Map<String, Object> params){
        appendAuthFilter(params);
        PageUtils page = dataTblWashProjService.queryRuleList(params);
        return R.ok().put("page", page);
    }

}
