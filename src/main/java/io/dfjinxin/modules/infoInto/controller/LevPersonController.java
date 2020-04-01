package io.dfjinxin.modules.infoInto.controller;

import com.alibaba.fastjson.JSON;
import io.dfjinxin.common.utils.Key2Name;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.validator.ValidatorUtils;
import io.dfjinxin.modules.infoInto.entity.LevPerson;
import io.dfjinxin.modules.infoInto.service.LevPersonService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/31 16:59
 * @Version: 1.0
 */
@RestController
@RequestMapping("/levPerson")
@Api(tags = "离汉人员统计")
@Slf4j
public class LevPersonController extends AbstractController {
    @Autowired
    private LevPersonService levPersonService;
//    @Autowired
//    private SysUserService sysUserService;

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody LevPerson entry) {

        log.info("新增-离吧人员:" + JSON.toJSONString(entry));
        ValidatorUtils.validateEntity(entry);
        if (!StringUtils.isEmpty(entry.getZoneCd())) {
            entry.setZoneCd(Key2Name.getNameByKey(entry.getZoneCd().trim()));
        }
        levPersonService.save(entry);
        return R.ok();
    }

    /**
     * 列表
     */
    @GetMapping("/pageList")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "返回数据集", required = false, dataType = "int", paramType = "query")
    })
    public R queryDataSourcesList(/*@RequestBody(required = false) LevPerson entry,*/
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        log.info("pageIndex:" + pageIndex);
        log.info("pageSize:" + pageSize);
//        log.info("查询对象:" + JSON.toJSONString(entry));


        Map<String, Object> params = new HashMap();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);

       /* SysUserEntity user = sysUserService.getById(super.getUserId());
        logger.info("*********当前登录用户信息:" + JSON.toJSONString(user));
        if (user != null) {
            String adminUser = user.getUsername();
            //暂定这两个是管理员，可以查询所有用户提交的数据。其它用户只能看到自己填报的数据
            if (!"admin".equals(adminUser) && !"wh_admin".equals(adminUser)) {
//                params.put("loginUserId", "");
                params.put("currUserId", super.getUserId().toString());
            }
        }*/

        PageUtils page = levPersonService.queryPage(params);
        return R.ok().put("page", page);
    }
}
