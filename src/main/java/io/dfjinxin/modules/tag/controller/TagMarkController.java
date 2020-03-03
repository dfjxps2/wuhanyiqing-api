package io.dfjinxin.modules.tag.controller;

import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.dfjinxin.modules.tag.service.TagMarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 打标签
 */
@RestController
@RequestMapping("tagMark")
@Api("打标签")
public class TagMarkController extends AbstractController {

    @Autowired
    private TagMarkService tagMarkService;

    @PostMapping("tableAutoMark")
    @ApiOperation("自动匹配【表】标签")
    public R tableAutoMark(@RequestBody List<String> tableIds){
        Map<Integer, List<Map<String, String>>> markResult = tagMarkService.tableAutoMark(tableIds, this.getTenantId());
        return R.ok().put("markResult",markResult);
    }

    @PostMapping("fieldAutoMark")
    @ApiOperation("自动匹配【字段】标签")
    public R fieldAutoMark(@RequestBody List<String> fieldIds){

        Map<Integer, List<Map<String, String>>> markResult = tagMarkService.fieldAutoMark(fieldIds, this.getTenantId());

        return R.ok().put("markResult",markResult);
    }
}
