package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.outsider.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/4/2 19:26
 * @Version: 1.0
 */

@RestController
@RequestMapping("/operate/log")
@Api(tags = "管理员操作日志")
@Slf4j
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 列表
     */
    @GetMapping("/pageList")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "返回数据集", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "time", value = "记录时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "日志类型", required = false, dataType = "String", paramType = "query")
    })
    public R getLogs(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "userName", required = false) String userName,
            @RequestParam(value = "time", required = false) String time
    ) {
        Map<String, Object> params = new HashMap();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        params.put("type", type);
        params.put("userName", userName);
        params.put("time", time);
        PageUtils page = logService.queryPage(params);
        return R.ok().put("page", page);
    }

}
