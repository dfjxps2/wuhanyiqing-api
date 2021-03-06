package io.dfjinxin.modules.infoInto.controller;

import com.alibaba.fastjson.JSON;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.validator.ValidatorUtils;
import io.dfjinxin.modules.infoInto.entity.T01DetainedPersonInfoEntity;
import io.dfjinxin.modules.infoInto.service.T01DetainedPersonInfoService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.sys.entity.SysUserEntity;
import io.dfjinxin.modules.sys.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @GetMapping("/pageList")
    @ApiOperation("分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "返回数据集", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keepStatusCd", value = "记录状态", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "submitDate", value = "填报日期", required = false, dataType = "String", paramType = "query"),
    })
    public R queryDataSourcesList(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(value = "submitDate", required = false) String submitDate,
            @RequestParam(value = "keepStatusCd", required = false) String keepStatusCd
    ) {
        logger.info("pageIndex:" + pageIndex);
        logger.info("pageSize:" + pageSize);
        logger.info("submitDate:" + submitDate);
        logger.info("keepStatusCd:" + keepStatusCd);

        Map<String, Object> params = new HashMap();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        params.put("keepStatusCd", keepStatusCd);
        params.put("submitDate", submitDate);

        SysUserEntity user = sysUserService.getById(super.getUserId());
        logger.info("*********当前登录用户信息:" + JSON.toJSONString(user));
        if (user != null) {
            String adminUser = user.getUsername();
            //暂定这两个是管理员，可以查询所有用户提交的数据。其它用户只能看到自己填报的数据
            if (!"admin".equals(adminUser) && !"wh_admin".equals(adminUser)) {
//                params.put("loginUserId", "");
                params.put("currUserId", super.getUserId().toString());
            }
        }

        PageUtils page = t01DetainedPersonInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation(value = "链接到详细页面", notes = "参数为主键id")
    public R info(@PathVariable("id") String id) {
        T01DetainedPersonInfoEntity t01DetainedPersonInfo = t01DetainedPersonInfoService.queryById(id);
        if (t01DetainedPersonInfo != null) {
            SysUserEntity reViewUser = sysUserService.getById(t01DetainedPersonInfo.getReviewUserId());
//        审核用户编号
            if (reViewUser != null) {
                t01DetainedPersonInfo.setReviewUser(reViewUser.getUsername());
            }
            SysUserEntity submitUser = sysUserService.getById(t01DetainedPersonInfo.getSubmitUserId());
            if (submitUser != null) {
//        填报用户编号
                t01DetainedPersonInfo.setSubmitUser(submitUser.getUsername());
            }
        }
        return R.ok().put("t01DetainedPersonInfo", t01DetainedPersonInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("新增")
    public R save(@RequestBody T01DetainedPersonInfoEntity t01DetainedPersonInfo) {

        logger.info("新增-填报对象:" + JSON.toJSONString(t01DetainedPersonInfo));
        ValidatorUtils.validateEntity(t01DetainedPersonInfo);

        Long userId = super.getUserId();
        SysUserEntity user = sysUserService.getById(userId);
        //新增为“1”
        t01DetainedPersonInfo.setKeepStatusCd("1");
        if (t01DetainedPersonInfo.getSubmitDate() == null) {
            t01DetainedPersonInfo.setSubmitDate(new Date());
        }

        //填报用户编号
        t01DetainedPersonInfo.setSubmitUserId(userId.toString());
        //经办人/负责人 填报人名称
        t01DetainedPersonInfo.setOrderName(user.getUsername());
        //填报人所属区域行政代码
        t01DetainedPersonInfo.setAreaCd(user.getOrgId());
        t01DetainedPersonInfoService.save(t01DetainedPersonInfo);

        return R.ok();
    }

    /**
     * 修改状态
     * 1-新增
     * 2-提交
     * 3-退回
     */
    @PostMapping("/updateStatus/{id}")
    @ApiOperation("更新状态-提交&退回功能")
    public R update(@PathVariable("id") Long id, @RequestParam("keepStatusCd") Integer keepStatusCd) {
        if (id == null || keepStatusCd == null) {
            return R.error("参数为空!");
        }

        T01DetainedPersonInfoEntity query = t01DetainedPersonInfoService.getById(id);
        if (query == null) return R.error("数据不存在!");

        T01DetainedPersonInfoEntity entity = new T01DetainedPersonInfoEntity();
        entity.setId(id);
        entity.setKeepStatusCd(keepStatusCd.toString());
        if (keepStatusCd == 2) {
            entity.setCommitDate(new Date());
            entity.setSubmitUserId(super.getUserId().toString());
        }

        //审核用户编号
        if (keepStatusCd == 3) {
            entity.setReviewDate(new Date());
            entity.setReviewUserId(super.getUserId().toString());
        }
//        entity.setUptDate(new Date());
        t01DetainedPersonInfoService.updateById(entity);

        return R.ok();
    }


    /**
     * 修改上报对象信息
     */
    @PostMapping("/updateInfo")
    @ApiOperation("修改上报对象信息")
    public R updateInfo(@RequestBody T01DetainedPersonInfoEntity t01DetainedPersonInfo) {
        t01DetainedPersonInfo.setUptDate(new Date());
        t01DetainedPersonInfo.setSubmitUserId(super.getUserId().toString());
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
