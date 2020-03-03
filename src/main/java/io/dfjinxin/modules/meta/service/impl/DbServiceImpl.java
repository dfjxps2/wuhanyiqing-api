package io.dfjinxin.modules.meta.service.impl;

import io.dfjinxin.modules.storage.dto.DateQueryItemDto;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.DbDao;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.meta.service.DbService;


@Service("dbService")
public class DbServiceImpl extends ServiceImpl<DbDao, DbEntity> implements DbService {

    @Autowired
    private DbDao dbDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DbEntity> page = this.page(
                new Query<DbEntity>().getPage(params),
                new QueryWrapper<DbEntity>()
        );

        return new PageUtils(page);
    }

//    @Override
//    public List<DbEntity> queryDbListByTnmt(Long tenantId) {
//        return baseMapper.queryDbListByTnmt(tenantId);
//    }

    @Override
    public List<DbEntity> queryDBListByDbid(List<TagDataTblEntity> tableList) {
        Set<Integer> dbidSet = new HashSet<>();
        for (TagDataTblEntity tblEntity : tableList) {
            dbidSet.add(tblEntity.getDbid());
        }
        return baseMapper.selectBatchIds(dbidSet);
    }

    @Override
    public List<DbEntity> queryDBListByPartid(Integer partid) {
        List<DbEntity> dbEntityList = dbDao.queryDBListByPartid(partid);
        return dbEntityList;
    }

    @Override
    public Map<String, Object> queryDBByCd(Long tnmtid, String dataType, Integer dbid) {
        return baseMapper.queryDBByCd(tnmtid, dataType, dbid);
    }

    @Override
    public DateQueryItemDto queryDBUBytblid(Integer tblid) {
        return baseMapper.queryDBUBytblid(tblid);
    }
}