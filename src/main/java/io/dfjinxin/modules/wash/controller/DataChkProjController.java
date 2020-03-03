package io.dfjinxin.modules.wash.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.UserContenUtils;
import io.dfjinxin.modules.hive.service.HiveService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.wash.dto.ProgramForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.wash.entity.DataChkProjEntity;
import io.dfjinxin.modules.wash.service.DataChkProjService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 数据检查项目
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@RestController
@RequestMapping("wash/datachkproj")
public class DataChkProjController extends AbstractController {
    @Autowired
    private DataChkProjService dataChkProjService;

    @Autowired
    private HiveService hiveService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("wash:datachkproj:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = dataChkProjService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 列表 分页
     */
    @RequestMapping("/page")
    @RequiresPermissions("wash:datachkproj:list")
    public R query(@RequestBody ProgramForm programForm){
        return R.ok().put("page", dataChkProjService.query(programForm, this.getTenantId()));
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{chkProjid}")
    @RequiresPermissions("wash:datachkproj:info")
    public R info(@PathVariable("chkProjid") Integer chkProjid){
		DataChkProjEntity dataChkProj = dataChkProjService.getById(chkProjid);

        return R.ok().put("dataChkProj", dataChkProj);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("wash:datachkproj:save")
    public R save(@RequestBody DataChkProjEntity dataChkProj){
        dataChkProj.setTnmtid(this.getTenantId().intValue());
		dataChkProjService.saveEntity(dataChkProj);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("wash:datachkproj:update")
    public R update(@RequestBody DataChkProjEntity dataChkProj){
		dataChkProjService.updateById(dataChkProj);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("wash:datachkproj:delete")
    public R delete(@RequestBody Integer[] chkProjids){
		dataChkProjService.removeByIds(Arrays.asList(chkProjids));

        return R.ok();
    }

    /**
     * 执行
     */
    @RequestMapping("/exec")
    @RequiresPermissions("wash:datachkproj:exec")
    public R exec(@RequestBody Map<String, Object> params){
        Object obj = hiveService.checkFunction(params.get("funcName").toString(), params.get("param").toString(), params.get("type").toString());

        return R.ok().put("data", obj);
    }

}
