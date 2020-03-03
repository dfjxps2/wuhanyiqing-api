package io.dfjinxin.modules.meta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.meta.dao.DataFldDao;
import io.dfjinxin.modules.meta.dao.DataFldMarkIdxDao;
import io.dfjinxin.modules.meta.dao.DataTblDao;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DataFldMarkIdxEntity;
import io.dfjinxin.modules.meta.entity.DataPartEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.meta.service.DataPartService;
import io.dfjinxin.modules.meta.service.DataTblService;
import io.dfjinxin.modules.storage.dto.DataTblReq;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.tag.dao.TagDataTblDao;
import io.dfjinxin.modules.tag.dao.TagInfoDao;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("dataTblService")
public class DataTblServiceImpl extends ServiceImpl<DataTblDao, DataTblEntity> implements DataTblService {

    @Autowired
    private DataTblDao dataTblDao;

    @Autowired
    private DataPartService dataPartService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<DataTblEntity> queryWrapper = getWhere(params);
        IPage<DataTblEntity> page = this.page(
                new Query<DataTblEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByRecord(DataTblReq dataTblReq, Long tnmtid) {
        if (dataTblReq.getPartid() == null) {
            throw new RRException("未获取到分区数据，无法执行相关查询！");
        }
        DataPartEntity dataPartEntity = dataPartService.getById(dataTblReq.getPartid());
        if (dataPartEntity == null || dataPartEntity.getTnmtid() != tnmtid.intValue()) {
            throw new RRException("分区：【" + dataTblReq.getPartid() + "】未归属本租户！");
        }

        Page pager = new Page(dataTblReq.getCurPage(), dataTblReq.getLimit());
        IPage<Map<String, Object>> pageData = dataTblDao.queryPageByRecord(pager, dataTblReq);
        return new PageUtils(pageData);
    }

    @Override
    public PageUtils listByParams(Map<String, Object> params) {
        Integer limit = (Integer) params.get("limit");
        Integer page = (Integer) params.get("page");
        int pageSize = 10;
        int pageIndex = 1;
        if (!StringUtils.isEmpty(limit) && !StringUtils.isEmpty(page)) {
            pageSize = limit;
            pageIndex = page;
            int startIndex = (pageIndex - 1) * pageSize;
            params.put("startPage", startIndex);
            params.put("pageSize", pageSize);
        }
        List<Map<String, String>> dataFldEntityList = dataTblDao.listByParams(params);
        int totalCount = dataTblDao.listByParamsCount(params);
        PageUtils returnPage = new PageUtils(dataFldEntityList, totalCount, pageSize,
                pageIndex);
        return returnPage;
    }

    @Override
    public List<Map<String, String>> getCount(Map<String, Object> params) {
        long tableCount = dataTblDao.getTableCount(params);
        long filedCount = dataTblDao.getFiledCount(params);
        String recordCount = dataTblDao.getRecordCount(params);
        long tableTagCount = dataTblDao.getTableTagCount(params);
        long filedTagCount = dataTblDao.getFiledTagCount(params);
        Map<String, String> map = new HashMap<>();
        map.put("tableCount", tableCount + "");
        map.put("filedCount", filedCount + "");
        map.put("recordCount", recordCount == null ? "0" : recordCount + "");
        map.put("tableTagCount", tableTagCount + "");
        map.put("filedTagCount", filedTagCount + "");
        List rsList = new ArrayList<Map<String, String>>();
        rsList.add(map);
        return rsList;
    }


    @Override
    public PageUtils getDetailByTableRow(Map<String, Object> params) {
        Page pager = new Page((Integer)params.get("curPage"), (Integer)params.get("limit"));
        IPage<Map<String, Object>> pageData ;
        Integer type=(Integer)params.get("type");//1 表数量，2字段数量，3记录数
        Integer isJoinLabel=(Integer)params.get("isJoinLabel");//是否加入标签关联，0没加入，1加入
        if (type == 1) {
            if(isJoinLabel==0){
                pageData = dataTblDao.getTableTagListDetail(pager,params);
            }else{
                pageData = dataTblDao.getTableTagListJoinLabelDetail(pager,params);
            }
        } else if (type == 2) {
            if(isJoinLabel==0){
                pageData = dataTblDao.getFiledTagListDetail(pager,params);
            }else{
                pageData = dataTblDao.getFiledTagListJoinLabelDetail(pager,params);
            }
        } else if (type == 3) {
            if(isJoinLabel==0){
                pageData = dataTblDao.getTableRecordListDetail(pager,params);
            }else{
                pageData = dataTblDao.getTableRecordListJoinLabelDetail(pager,params);
            }
        } else {
            //return null;
            pageData=null;
        }
        return new PageUtils(pageData);
    }

    @Override
    public List<Map<String, String>> getTagDataList(Integer type, Integer partId, String dbId, String tableName,long tnmtId) {
        if (type == 1) {
            return dataTblDao.getTableTagList(partId, dbId, tableName, tnmtId);
        } else if (type == 2) {
            return dataTblDao.getFiledTagList(partId, dbId, tableName, tnmtId);
        } else if (type == 3) {
            return dataTblDao.getTableRecordList(partId, dbId, tableName, tnmtId);
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String, String>> getTagDataListJoinLabel(Integer type, Integer partId, String dbId, ArrayList<Object> labelIdList,long tnmtId) {
        if (type == 1) {
            return dataTblDao.getTableTagListJoinLabel(partId, dbId, labelIdList,tnmtId);
        } else if (type == 2) {
            return dataTblDao.getFiledTagListJoinLabel(partId, dbId, labelIdList,tnmtId);
        } else if (type == 3) {
            return dataTblDao.getTableRecordListJoinLabel(partId, dbId, labelIdList,tnmtId);
        } else {
            return null;
        }
    }

    private QueryWrapper<DataTblEntity> getWhere(Map<String, Object> params) {
        String q = params.containsKey("q") ? params.get("q").toString().toLowerCase() : "";
        QueryWrapper<DataTblEntity> where = new QueryWrapper<DataTblEntity>();
        if (q.length() > 0) {
            where.and(w -> w.like("lower(Data_Tbl_Phys_Nm)", q).or().like("Data_Tbl_Cn_Nm", q));
        }

        String tenantId = params.containsKey("tenantId") ? params.get("tenantId").toString() : "0";
        where.and(w -> w.inSql("Dbid", "select Dbid from db left join data_part p on db.Partid=p.Partid where p.Tnmtid=" + tenantId));

        return where;
    }
}