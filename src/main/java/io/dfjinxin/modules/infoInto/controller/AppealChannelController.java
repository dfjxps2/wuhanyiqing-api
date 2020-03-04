package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T99AppealChannelEntity;
import io.dfjinxin.modules.infoInto.service.T99AppealChannelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 诉求渠道代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:15:02
 */
@RestController
@RequestMapping("base/appealChannel")
@Api(tags = "诉求渠道代码表")
public class AppealChannelController {
    @Autowired
    private T99AppealChannelService t99AppealChannelService;

    /**
     * 列表
     */
    @GetMapping("/getData")
    public R list() {
        List<T99AppealChannelEntity> list = t99AppealChannelService.list();

        return R.ok().put("data", list);
    }


//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{appealChannelCd}")
//    public R info(@PathVariable("appealChannelCd") String appealChannelCd) {
//        T99AppealChannelEntity t99AppealChannel = t99AppealChannelService.getById(appealChannelCd);
//
//        return R.ok().put("t99AppealChannel", t99AppealChannel);
//    }

//    /**
//     * 保存
//     */
//    @RequestMapping("/save")
//    @RequiresPermissions("infoInto:t99appealchannel:save")
//    public R save(@RequestBody T99AppealChannelEntity t99AppealChannel) {
//        t99AppealChannelService.save(t99AppealChannel);
//
//        return R.ok();
//    }
//
//    /**
//     * 修改
//     */
//    @RequestMapping("/update")
//    @RequiresPermissions("infoInto:t99appealchannel:update")
//    public R update(@RequestBody T99AppealChannelEntity t99AppealChannel) {
//        t99AppealChannelService.updateById(t99AppealChannel);
//
//        return R.ok();
//    }
//
//    /**
//     * 删除
//     */
//    @RequestMapping("/delete")
//    @RequiresPermissions("infoInto:t99appealchannel:delete")
//    public R delete(@RequestBody String[] appealChannelCds) {
//        t99AppealChannelService.removeByIds(Arrays.asList(appealChannelCds));
//
//        return R.ok();
//    }

}
