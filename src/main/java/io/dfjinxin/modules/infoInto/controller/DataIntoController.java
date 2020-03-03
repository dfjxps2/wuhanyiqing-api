package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T01DetainedPersonInfoEntity;
import io.dfjinxin.modules.infoInto.service.T01DetainedPersonInfoService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/3 15:15
 * @Version: 1.0
 */

@RestController
@RequestMapping("/dataInto")
@Api(tags = "区县填报-主页面")
public class DataIntoController extends AbstractController {

    @Autowired
    private T01DetainedPersonInfoService t01DetainedPersonInfoService;

    /**
     * 列表
     */
    @GetMapping("/pageList")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "返回数据集", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keepStatusCd", value = "填报日志", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "submitDate", value = "记录状态", required = false, dataType = "int", paramType = "query"),
    })
    public R queryDataSourcesList(
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
            @RequestParam(value = "submitDate", required = false) String submitDate,
            @RequestParam(value = "keepStatusCd", required = false) Integer keepStatusCd
    ) {
        Map<String, Object> params = new HashMap();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        params.put("keepStatusCd", keepStatusCd);
        params.put("submitDate", submitDate);
        PageUtils page = t01DetainedPersonInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "链接到详细页面",notes = "参数为主键id")
//    @RequiresPermissions("analyse:t01detainedpersoninfo:info")
    public R info(@PathVariable("id") String id){
        T01DetainedPersonInfoEntity t01DetainedPersonInfo = t01DetainedPersonInfoService.getById(id);

        return R.ok().put("t01DetainedPersonInfo", t01DetainedPersonInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("新增")
//    @RequiresPermissions("analyse:t01detainedpersoninfo:save")
    public R save(@RequestBody T01DetainedPersonInfoEntity t01DetainedPersonInfo){
        t01DetainedPersonInfoService.save(t01DetainedPersonInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改")
//    @RequiresPermissions("analyse:t01detainedpersoninfo:update")
    public R update(@RequestBody T01DetainedPersonInfoEntity t01DetainedPersonInfo){
        t01DetainedPersonInfoService.updateById(t01DetainedPersonInfo);

        return R.ok();
    }

    /**
     * 删除
     */
//    @RequestMapping("/delete")
////    @RequiresPermissions("analyse:t01detainedpersoninfo:delete")
//    public R delete(@RequestBody String[] ids){
//        t01DetainedPersonInfoService.removeByIds(Arrays.asList(ids));
//
//        return R.ok();
//    }

}