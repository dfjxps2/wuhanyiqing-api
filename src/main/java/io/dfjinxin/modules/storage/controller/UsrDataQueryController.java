package io.dfjinxin.modules.storage.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.dfjinxin.common.annotation.RequiresPermissions;
import io.dfjinxin.modules.meta.dto.DataPartDto;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.meta.service.DataPartService;
import io.dfjinxin.modules.meta.service.DataTblService;
import io.dfjinxin.modules.meta.service.DbService;
import io.dfjinxin.modules.storage.dto.DataQueryReq;
import io.dfjinxin.modules.storage.dto.DataTblReq;
import io.dfjinxin.modules.storage.dto.UsrDataQueryDto;
import io.dfjinxin.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dfjinxin.modules.storage.service.UsrDataQueryService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;

/**
 * 用户数据查询
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:17:04
 */
@RestController
@RequestMapping("storage/usrdataquery")
public class UsrDataQueryController extends AbstractController {
    @Autowired
    private UsrDataQueryService usrDataQueryService;

    @Autowired
    private DataTblService dataTblService;

    @Autowired
    private DataPartService dataPartService;

    @Autowired
    private DbService dbService;



    /**
     * 列表 所有
     */
    @RequestMapping("/data-query/list")
    @RequiresPermissions("storage:usrdataquery:list")
    public R DataQueryList(){

        List<UsrDataQueryDto> list = usrDataQueryService.queryList(this.getUserId());

        return R.ok().put("list", list);
    }

    /**
     * 数据分区列表
     */
    @RequestMapping("/part/list")
    @RequiresPermissions("storage:usrdataquery:list")
    public R partList(){
        List<DataPartDto> dataPartDtoList = dataPartService.selectPartByTnmt(this.getTenantId());

        return R.ok().put("list", dataPartDtoList);
    }

    /**
     * 数据分区列表
     */
    @RequestMapping("/db/list")
    @RequiresPermissions("storage:usrdataquery:list")
    public R dbList(Integer partid){
        List<DbEntity> dbEntityList = dbService.queryDBListByPartid(partid);

        return R.ok().put("list", dbEntityList);
    }

    /**
     * 表查询 分页
     */
    @RequestMapping("/tbl/page")
    @RequiresPermissions("storage:usrdataquery:list")
    public R pageTbl(@RequestBody DataTblReq dataTblReq){
        PageUtils page = dataTblService.queryPageByRecord(dataTblReq, this.getTenantId());

        return R.ok().put("page", page);
    }

    /**
     * 表字段列表
     */
    @RequestMapping("/fld/list")
    @RequiresPermissions("storage:usrdataquery:list")
    public R fldList(Integer tblid){
        Map<String, Object> result = usrDataQueryService.dataTblFld(tblid);

        return R.ok().put("data", result);
    }

    /**
     * 查询表数据
     */
    @RequestMapping("/data/query")
    @RequiresPermissions("storage:usrdataquery:list")
    public R dataQuery(@RequestBody DataQueryReq dataQueryReq){
        PageUtils page  = usrDataQueryService.queryTblData(dataQueryReq, this.getTenantId(), false);//第一次查询
        return R.ok().put("page", page);
    }

    /**
     * 保存
     */
    @RequestMapping("/data-query/save")
    @RequiresPermissions("storage:usrdataquery:save")
    public R dataQuerySave(@RequestBody UsrDataQueryDto usrDataQueryDto){
		usrDataQueryService.saveDataQuery(usrDataQueryDto, this.getUserId());
        List<UsrDataQueryDto> list = usrDataQueryService.queryList(this.getUserId());
        return R.ok().put("list", list);
    }

    /**
     * 删除
     */
    @RequestMapping("/data-query/delete")
    @RequiresPermissions("storage:usrdataquery:delete")
    public R dataQueryDelete(@RequestBody Integer[] usrQueryids){
		usrDataQueryService.removeByIds(Arrays.asList(usrQueryids));
        List<UsrDataQueryDto> list = usrDataQueryService.queryList(this.getUserId());
        return R.ok().put("list", list);
    }

}
