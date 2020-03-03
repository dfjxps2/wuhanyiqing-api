package io.dfjinxin.modules.meta.controller;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.meta.service.DbService;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 数据库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@RestController
@RequestMapping("meta/db")
public class DbController extends AbstractController {
    @Autowired
    private DbService dbService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("meta:db:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = dbService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据租户id查询所属该租户的数据库
     * modify by zhc 7.1
     */
    /*@RequestMapping("/getDbListByTnmt")
    public R queryDbListByTnmt(@RequestParam Map<String, Object> params) {

        List<DbEntity> dbEntityList = dbService.queryDbListByTnmt(this.getTenantId());

        return R.ok().put("data", dbEntityList);
    }*/


    /**
     * 信息
     */
    @RequestMapping("/info/{dbid}")
    @RequiresPermissions("meta:db:info")
    public R info(@PathVariable("dbid") Integer dbid) {
        DbEntity db = dbService.getById(dbid);

        return R.ok().put("db", db);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("meta:db:save")
    public R save(@RequestBody DbEntity db) {
        dbService.save(db);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("meta:db:update")
    public R update(@RequestBody DbEntity db) {
        dbService.updateById(db);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("meta:db:delete")
    public R delete(@RequestBody Integer[] dbids) {
        dbService.removeByIds(Arrays.asList(dbids));

        return R.ok();
    }

}
