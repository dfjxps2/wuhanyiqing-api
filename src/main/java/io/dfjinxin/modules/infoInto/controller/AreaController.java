package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T99AreaEntity;
import io.dfjinxin.modules.infoInto.service.T99AreaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 行政区域代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:06
 */
@RestController
@RequestMapping("base/area")
@Api(tags = "行政区域代码表")
public class AreaController {
    @Autowired
    private T99AreaService t99AreaService;

    /**
     * 列表
     */
    @GetMapping("/getData")
    public R list(){
        List<T99AreaEntity> list = t99AreaService.list();

        return R.ok().put("data", list);
    }

//
//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{areaCd}")
//    @RequiresPermissions("infoInto:t99area:info")
//    public R info(@PathVariable("areaCd") String areaCd){
//		T99AreaEntity t99Area = t99AreaService.getById(areaCd);
//
//        return R.ok().put("t99Area", t99Area);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("infoInto:t99area:save")
//    public R save(@RequestBody T99AreaEntity t99Area){
//		t99AreaService.save(t99Area);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("infoInto:t99area:update")
//    public R update(@RequestBody T99AreaEntity t99Area){
//		t99AreaService.updateById(t99Area);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("infoInto:t99area:delete")
//    public R delete(@RequestBody String[] areaCds){
//		t99AreaService.removeByIds(Arrays.asList(areaCds));
//
//        return R.ok();
//    }

}
