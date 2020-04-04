package io.dfjinxin.modules.infoInto.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import io.dfjinxin.common.utils.Key2Name;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.validator.ValidatorUtils;
import io.dfjinxin.modules.infoInto.entity.LevPerson;
import io.dfjinxin.modules.infoInto.entity.LevPersonReqDto;
import io.dfjinxin.modules.infoInto.service.LevPersonService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 列表
     */
    @PostMapping("/pageList")
    @ApiOperation("分页查询")
    public R queryList(@RequestBody LevPersonReqDto reqDto) {
        log.info("the req reqDto:{}", JSON.toJSONString(reqDto));
        if (reqDto.getPageIndex() == null) reqDto.setPageIndex(1);
        if (reqDto.getPageSize() == null) reqDto.setPageSize(20);

        //当前登录用户
        if(StringUtils.isEmpty(reqDto.getZoneCd())){
            return R.error("用户名不能为空!");
        }
        //这2个用户可查所有
        String userName = reqDto.getZoneCd();
        if ("admin".equals(userName) || "wh_admin".equals(userName)) {
            reqDto.setZoneCd(null);
        }else {
            reqDto.setZoneCd(Key2Name.getNameByKey(reqDto.getZoneCd().trim()));
        }
        Map<String, Object> map = beanToMap(reqDto);
        PageUtils page = levPersonService.queryPage(map);
        return R.ok().put("page", page);
    }

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

}
