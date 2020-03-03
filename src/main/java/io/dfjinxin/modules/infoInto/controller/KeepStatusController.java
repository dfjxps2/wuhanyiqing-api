package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T99KeepStatusEntity;
import io.dfjinxin.modules.infoInto.service.T99KeepStatusService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 记录状态代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-03 15:46:17
 */
@RestController
@RequestMapping("/base/keepstatus")
@Api(tags = "记录状态代码表")
public class KeepStatusController {
    @Autowired
    private T99KeepStatusService t99KeepStatusService;

    /**
     * 列表
     */
    @GetMapping("/getStatus")
//    @RequiresPermissions("analyse:t99keepstatus:list")
    public R list(){
        List<T99KeepStatusEntity> data = t99KeepStatusService.list();

        return R.ok().put("data", data);
    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{keepStatusCd}")
//    @RequiresPermissions("analyse:t99keepstatus:info")
//    public R info(@PathVariable("keepStatusCd") String keepStatusCd){
//		T99KeepStatusEntity t99KeepStatus = t99KeepStatusService.getById(keepStatusCd);
//
//        return R.ok().put("t99KeepStatus", t99KeepStatus);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("analyse:t99keepstatus:save")
//    public R save(@RequestBody T99KeepStatusEntity t99KeepStatus){
//		t99KeepStatusService.save(t99KeepStatus);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("analyse:t99keepstatus:update")
//    public R update(@RequestBody T99KeepStatusEntity t99KeepStatus){
//		t99KeepStatusService.updateById(t99KeepStatus);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("analyse:t99keepstatus:delete")
//    public R delete(@RequestBody String[] keepStatusCds){
//		t99KeepStatusService.removeByIds(Arrays.asList(keepStatusCds));
//
//        return R.ok();
//    }

}
