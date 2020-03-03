
package io.dfjinxin.modules.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.meta.dao.DataFldDao;
import io.dfjinxin.modules.meta.dao.DataPartDao;
import io.dfjinxin.modules.meta.dto.DataPartDto;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.meta.service.DbService;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.storage.utils.StorageUtils;
import io.dfjinxin.modules.tag.dao.TagDataTblDao;
import io.dfjinxin.modules.tag.dao.TagInfoDao;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import io.dfjinxin.modules.tag.service.TagDataTblService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("tagDataTblService")
public class TagDataTblServiceImpl extends ServiceImpl<TagDataTblDao, TagDataTblEntity> implements TagDataTblService {

    @Autowired
    private TagDataTblDao tagDataTblDao;

    @Autowired
    private DataFldDao dataFldDao;

    @Autowired
    private TagInfoDao tagInfoDao;

    @Autowired
    private DataPartDao dataPartDao;

    @Autowired
    private DbService dbService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TagDataTblEntity> page = this.page(new Query<TagDataTblEntity>().getPage(params), new QueryWrapper<TagDataTblEntity>());

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryByCriteria(DesenForm desenForm, Long tenantId) {

        //校验所选分区是否属于当前租户
        List<DataPartDto> dataPartDtos = dataPartDao.selectPartByTnmt(tenantId);
        if (!StorageUtils.isMyPart2(dataPartDtos, desenForm.getPartid())) {
            throw new RRException("分区：" + desenForm.getPartid() + "未归属本租户！");
        }

        int totalCount = tagDataTblDao.queryCountByCriteria(desenForm);
        List<TagDataTblEntity> tableList = tagDataTblDao.queryListByCriteria(desenForm);

        List<DbEntity> dbList = new ArrayList<>();
        if (!tableList.isEmpty()) {
            dbList = dbService.queryDBListByDbid(tableList);
        }

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("tblList", tableList);
        returnMap.put("dbList", dbList);
        List returnList = new ArrayList();
        returnList.add(returnMap);

        return new PageUtils(returnList, totalCount, desenForm.getPageSize(), desenForm.getPageIndex());

    }

    @Override
    public PageUtils getTblAndFldInfoListbyTblIds(DesenForm desenForm,Integer tenantId) {

        int totalCount = this.dataFldDao.getFldsCountPageByDataTblId(desenForm);
        List<DataFldEntity> dataFldEntityList = this.dataFldDao.getFldsListPageByDataTblId(desenForm);
        //2，遍历每一个字段
        List fldsList = new ArrayList();
        for (DataFldEntity dataFldEntity : dataFldEntityList) {
            Map fldMap = new HashMap();
            //字段名称
            fldMap.put("fldName", dataFldEntity.getFldPhysNm());
            fldMap.put("fldCnName", dataFldEntity.getFldCnNm());
            fldMap.put("fldId", dataFldEntity.getFldid());
            //表名称
            fldMap.put("tblName", dataFldEntity.getDataTblPhysNm());
            fldMap.put("tblCnName", dataFldEntity.getDataTblCnNm());
            fldMap.put("tblId", dataFldEntity.getDataTblid());
//            数据库名称
            fldMap.put("dbCnName", dataFldEntity.getDbCnNm());
            //3,统计每个字段的标签
            List<TagInfoEntity> labels = this.getLabelsByFldId(dataFldEntity.getFldid(),tenantId);
            fldMap.put("labels", labels);
            fldMap.put("fldOrd",dataFldEntity.getFldOrd());
            fldsList.add(fldMap);

        }
        return new PageUtils(fldsList, totalCount, desenForm.getPageSize(), desenForm.getPageIndex());
    }

    @Override
    public PageUtils queryLabelViewInfoByPage(DesenForm desenForm, Integer tenantId) {

        List fldsList = new ArrayList();
        int totalCount = dataFldDao.queryDataFldMarkIdxCount(desenForm);
        List<DataFldEntity> dataFldEntityList = dataFldDao.queryDataFldMarkIdxList(desenForm);
        for (DataFldEntity dataFldEntity : dataFldEntityList) {
            Map<String, Object> fldMap = new HashMap();
            //字段名称
            fldMap.put("fldName", dataFldEntity.getFldPhysNm());
            fldMap.put("fldCnName", dataFldEntity.getFldCnNm());
            fldMap.put("fldId", dataFldEntity.getFldid());
            //表名称
            fldMap.put("tblName", dataFldEntity.getDataTblPhysNm());
            fldMap.put("tblCnName", dataFldEntity.getDataTblCnNm());
            fldMap.put("tblId", dataFldEntity.getDataTblid());
            List<TagInfoEntity> labels = this.getLabelsByFldId(dataFldEntity.getFldid(),tenantId);
            fldMap.put("labels", labels);
            fldMap.put("dbCnName", dataFldEntity.getDbCnNm());
            fldMap.put("fldOrd",dataFldEntity.getFldOrd());
            fldsList.add(fldMap);
        }

        return new PageUtils(fldsList, totalCount, desenForm.getPageSize(), desenForm.getPageIndex());
    }

    private List<TagInfoEntity> getLabelsByFldId(Integer fldid, Integer tenantId) {
        return tagInfoDao.queryLabelsByFldId(fldid, tenantId);
    }

}