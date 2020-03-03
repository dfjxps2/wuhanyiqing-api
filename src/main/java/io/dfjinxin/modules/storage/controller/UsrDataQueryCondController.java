package io.dfjinxin.modules.storage.controller;

import java.util.Arrays;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;
import io.dfjinxin.modules.storage.service.UsrDataQueryCondService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;



/**
 * 用户数据查询条件
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:19:07
 */
@RestController
@RequestMapping("storage/usrdataquerycond")
public class UsrDataQueryCondController {
    @Autowired
    private UsrDataQueryCondService usrDataQueryCondService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("storage:usrdataquerycond:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usrDataQueryCondService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{usrDataQueryCondid}")
    @RequiresPermissions("storage:usrdataquerycond:info")
    public R info(@PathVariable("usrDataQueryCondid") Integer usrDataQueryCondid){
		UsrDataQueryCondEntity usrDataQueryCond = usrDataQueryCondService.getById(usrDataQueryCondid);

        return R.ok().put("usrDataQueryCond", usrDataQueryCond);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("storage:usrdataquerycond:save")
    public R save(@RequestBody UsrDataQueryCondEntity usrDataQueryCond){
		usrDataQueryCondService.save(usrDataQueryCond);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("storage:usrdataquerycond:update")
    public R update(@RequestBody UsrDataQueryCondEntity usrDataQueryCond){
		usrDataQueryCondService.updateById(usrDataQueryCond);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("storage:usrdataquerycond:delete")
    public R delete(@RequestBody Integer[] usrDataQueryCondids){
		usrDataQueryCondService.removeByIds(Arrays.asList(usrDataQueryCondids));

        return R.ok();
    }

}
