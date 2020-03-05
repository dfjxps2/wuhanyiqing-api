package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.sys.entity.SysOrgEntity;
import io.dfjinxin.modules.sys.service.SysOrgService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 武汉市行政区域代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:06
 */
@RestController
@RequestMapping("base/wuhanArea")
@Api(tags = "武汉市行政区域码表")
public class WuHanAreaController {

    @Autowired
    private SysOrgService sysOrgService;
    /**
     * 列表
     */
    @GetMapping("/getData")
    public R list(){
//        List<SysOrgEntity> list = sysOrgService.list();
        List<SysOrgEntity> list = sysOrgService.queryOrg();

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
