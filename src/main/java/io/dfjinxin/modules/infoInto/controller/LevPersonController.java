package io.dfjinxin.modules.infoInto.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.List;
import java.util.Map;

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


   /* @PostMapping("/queryByEntry")
    @ApiOperation("查询test")
    public R query(@RequestBody LevPerson entry) {

        log.info("新增-离吧人员:" + JSON.toJSONString(entry));
        LambdaQueryWrapper<LevPerson> queryWrapper = new LambdaQueryWrapper();
        queryWrapper
                .eq(!StringUtils.isEmpty(entry.getName()), LevPerson::getName, entry.getName())
                .eq(!StringUtils.isEmpty(entry.getPhone()), LevPerson::getPhone, entry.getPhone());
        List<LevPerson> list = levPersonService.list(queryWrapper);
        return R.ok().put("data", list);
    }*/

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

        Map<String, Object> params = new HashMap();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        PageUtils page = levPersonService.queryPage(params);
        return R.ok().put("page", page);
    }
}
