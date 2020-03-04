package io.dfjinxin.modules.infoInto.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.infoInto.entity.T99DetainedPersonTypeEntity;
import io.dfjinxin.modules.infoInto.service.T99DetainedPersonTypeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 滞留人员类型代码表
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-04 17:46:17
 */
@RestController
@RequestMapping("/base/detainedPersonType")
@Api(tags = "滞留人员类型代码表")
public class DetainedPersonTypeController {
    @Autowired
    private T99DetainedPersonTypeService t99DetainedPersonTypeService;

    /**
     * 列表
     */
    @GetMapping("/getData")
    public R list() {
        List<T99DetainedPersonTypeEntity> data = t99DetainedPersonTypeService.list();

        return R.ok().put("data", data);
    }

}
