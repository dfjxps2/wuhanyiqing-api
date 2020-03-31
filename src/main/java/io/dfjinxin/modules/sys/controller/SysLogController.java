/**
 * 2019 东方金信
 */

package io.dfjinxin.modules.sys.controller;

import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.sys.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/log")
@Api(tags = "系统日志")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiImplicitParam(name = "userName", value = "用户名", required = false, dataType = "String", paramType = "query")
    public R list(@RequestParam(required = false) String userName) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        PageUtils page = sysLogService.queryPage(map);
        return R.ok().put("page", page);
    }

}
