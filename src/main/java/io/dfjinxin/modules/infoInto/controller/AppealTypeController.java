package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T99AppealTypeEntity;
import io.dfjinxin.modules.infoInto.service.T99AppealTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 诉求类型代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:14:35
 */
@RestController
@RequestMapping("base/appealtype")
@Api(tags = "诉求类型代码表")
public class AppealTypeController {
    @Autowired
    private T99AppealTypeService t99AppealTypeService;

    /**
     * 列表
     */
    @GetMapping("/getData")
    public R list(){
        List<T99AppealTypeEntity> list = t99AppealTypeService.list();

        return R.ok().put("data", list);
    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{appealTypeCd}")
//    @RequiresPermissions("infoInto:t99appealtype:info")
//    public R info(@PathVariable("appealTypeCd") String appealTypeCd){
//		T99AppealTypeEntity t99AppealType = t99AppealTypeService.getById(appealTypeCd);
//
//        return R.ok().put("t99AppealType", t99AppealType);
//    }
//
//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("infoInto:t99appealtype:save")
//    public R save(@RequestBody T99AppealTypeEntity t99AppealType){
//		t99AppealTypeService.save(t99AppealType);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("infoInto:t99appealtype:update")
//    public R update(@RequestBody T99AppealTypeEntity t99AppealType){
//		t99AppealTypeService.updateById(t99AppealType);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("infoInto:t99appealtype:delete")
//    public R delete(@RequestBody String[] appealTypeCds){
//		t99AppealTypeService.removeByIds(Arrays.asList(appealTypeCds));
//
//        return R.ok();
//    }

}
