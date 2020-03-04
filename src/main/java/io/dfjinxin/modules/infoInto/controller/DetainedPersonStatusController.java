package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T99DetainedPersonStatusEntity;
import io.dfjinxin.modules.infoInto.service.T99DetainedPersonStatusService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;



/**
 * 滞留人员状态代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:13:36
 */
@RestController
@RequestMapping("base/detainedPersonStatus")
@Api(tags = "滞留人员状态代码表")
public class DetainedPersonStatusController {
    @Autowired
    private T99DetainedPersonStatusService t99DetainedPersonStatusService;

    /**
     * 列表
     */
    @GetMapping("/getData")
    public R list(){
        List<T99DetainedPersonStatusEntity> data = t99DetainedPersonStatusService.list();


        return R.ok().put("data", data);
    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{detainedPersonStatusCd}")
//    @RequiresPermissions("infoInto:t99detainedpersonstatus:info")
//    public R info(@PathVariable("detainedPersonStatusCd") String detainedPersonStatusCd){
//		T99DetainedPersonStatusEntity t99DetainedPersonStatus = t99DetainedPersonStatusService.getById(detainedPersonStatusCd);
//
//        return R.ok().put("t99DetainedPersonStatus", t99DetainedPersonStatus);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("infoInto:t99detainedpersonstatus:save")
//    public R save(@RequestBody T99DetainedPersonStatusEntity t99DetainedPersonStatus){
//		t99DetainedPersonStatusService.save(t99DetainedPersonStatus);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("infoInto:t99detainedpersonstatus:update")
//    public R update(@RequestBody T99DetainedPersonStatusEntity t99DetainedPersonStatus){
//		t99DetainedPersonStatusService.updateById(t99DetainedPersonStatus);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("infoInto:t99detainedpersonstatus:delete")
//    public R delete(@RequestBody String[] detainedPersonStatusCds){
//		t99DetainedPersonStatusService.removeByIds(Arrays.asList(detainedPersonStatusCds));
//
//        return R.ok();
//    }

}
