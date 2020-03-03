package io.dfjinxin.modules.storage.service.impl;

import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.SSH;
import io.dfjinxin.config.SystemParams;
import io.dfjinxin.modules.hive.service.HiveService;
import io.dfjinxin.modules.meta.dao.DpDao;
import io.dfjinxin.modules.meta.entity.*;
import io.dfjinxin.modules.meta.service.*;
import io.dfjinxin.modules.storage.dto.DataQueryReq;
import io.dfjinxin.modules.storage.dto.DateQueryItemDto;
import io.dfjinxin.modules.storage.dto.UsrDataQueryDto;
import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;
import io.dfjinxin.modules.storage.service.UsrDataQueryCondService;
import io.dfjinxin.modules.storage.utils.StorageUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.storage.dao.UsrDataQueryDao;
import io.dfjinxin.modules.storage.entity.UsrDataQueryEntity;
import io.dfjinxin.modules.storage.service.UsrDataQueryService;
import org.springframework.transaction.annotation.Transactional;


@Service("usrDataQueryService")
public class UsrDataQueryServiceImpl extends ServiceImpl<UsrDataQueryDao, UsrDataQueryEntity> implements UsrDataQueryService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsrDataQueryCondService usrDataQueryCondService;

    @Autowired
    private DataTblService dataTblService;

    @Autowired
    private DataFldService dataFldService;

    @Autowired
    private DbService dbService;

    @Autowired
    private HiveService hiveService;

    @Autowired
    private SystemParams systemParams;

    @Autowired
    private DpDao dpDao;
    @Autowired
    private DpService dpService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsrDataQueryEntity> page = this.page(
                new Query<UsrDataQueryEntity>().getPage(params),
                new QueryWrapper<UsrDataQueryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询出当前登录用户所有查询名称
     * 再根据dataTblid依次去查询表、该表的字段
     *
     * @return
     */
    @Override
    public List<UsrDataQueryDto> queryList(String userId) {

        List<UsrDataQueryDto> usrDataQueryDtoList = new ArrayList<>();

        QueryWrapper<UsrDataQueryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Usr_Lognid", userId);
        List<UsrDataQueryEntity> dataQueryEntityList = this.list(queryWrapper);

        if (dataQueryEntityList != null && !dataQueryEntityList.isEmpty()) {
            UsrDataQueryDto usrDataQueryDto;
            QueryWrapper<UsrDataQueryCondEntity> queryCondWrapper;
            QueryWrapper<DataFldEntity> dataFldWrapper;
            for (UsrDataQueryEntity entity : dataQueryEntityList) {
                //查询条件
                queryCondWrapper = new QueryWrapper<>();
                queryCondWrapper.eq("Usr_Queryid", entity.getUsrQueryid());
                List<UsrDataQueryCondEntity> dataQueryCondEntityList = usrDataQueryCondService.list(queryCondWrapper);

                DateQueryItemDto dateQueryItemDto = dbService.queryDBUBytblid(entity.getDataTblid());

                usrDataQueryDto = new UsrDataQueryDto();
                usrDataQueryDto.setDataQueryEntity(entity);
                usrDataQueryDto.setDataQueryCondEntityList(dataQueryCondEntityList);
                usrDataQueryDto.setDateQueryItemDto(dateQueryItemDto);
                usrDataQueryDtoList.add(usrDataQueryDto);
            }
        }
        return usrDataQueryDtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveDataQuery(UsrDataQueryDto usrDataQueryDto, String userId) {
        if (StringUtils.isEmpty(usrDataQueryDto.getDataQueryEntity().getUsrDataQueryNm())) {
            throw new RRException("保存失败：查询名称不能为空");
        }
        UsrDataQueryEntity usrDataQueryEntity = baseMapper.selectByUserAndName(userId, usrDataQueryDto.getDataQueryEntity().getUsrDataQueryNm().trim());

        //如果存在记录而且主键不相同
        if (null != usrDataQueryEntity) {
            if (!usrDataQueryEntity.getUsrQueryid().equals(usrDataQueryDto.getDataQueryEntity().getUsrQueryid())) {
                throw new RRException("保存失败：该查询名称已存在");
            }
        }

        usrDataQueryDto.getDataQueryEntity().setDataTblid(usrDataQueryDto.getDateQueryItemDto().getDataTblid());
        usrDataQueryDto.getDataQueryEntity().setUsrLognid(userId + "");
        Integer usrQueryid = usrDataQueryDto.getDataQueryEntity().getUsrQueryid();
        //如果dataQueryid不为空则修改，为空则插入
        if (usrQueryid == null) {
            this.save(usrDataQueryDto.getDataQueryEntity());
            usrQueryid = usrDataQueryDto.getDataQueryEntity().getUsrQueryid();
        } else {
            this.updateById(usrDataQueryDto.getDataQueryEntity());
        }

        //先删除所有条件，再根据dataQueryCondid是否为空决定插入还是修改
        QueryWrapper<UsrDataQueryCondEntity> queryCondWrapper = new QueryWrapper<>();
        queryCondWrapper.eq("Usr_Queryid", usrQueryid);
        usrDataQueryCondService.remove(queryCondWrapper);
        if (usrDataQueryDto.getDataQueryCondEntityList() != null && !usrDataQueryDto.getDataQueryCondEntityList().isEmpty()) {

            UsrDataQueryCondEntity condEntity;
            for (int i = 0; i < usrDataQueryDto.getDataQueryCondEntityList().size(); i++) {
                condEntity = usrDataQueryDto.getDataQueryCondEntityList().get(i);
                if (StringUtils.isNotEmpty(condEntity.getOperQty())) {
                    condEntity.setOperQty(condEntity.getOperQty().trim());
                }
                condEntity.setUsrQueryid(usrQueryid);
                condEntity.setQueryCondOrd(i + 1);
                usrDataQueryCondService.save(condEntity);

            }
        }
        return false;
    }

    @Override
    public Map<String, Object> dataTblFld(Integer tblid) {

        DateQueryItemDto dateQueryItemDto = dbService.queryDBUBytblid(tblid);

        QueryWrapper<DataFldEntity> fldWrapper = new QueryWrapper<>();
        fldWrapper.eq("Data_Tblid", tblid);
        fldWrapper.orderByAsc("Fld_Ord");
        List<DataFldEntity> dataFldEntityList = dataFldService.list(fldWrapper);

        List<DpEntity> dps = dpDao.queryDpEntityById(tblid);

        Map<String, Object> result = new HashMap<>();
        result.put("fld", dataFldEntityList);
        result.put("dps", dps);
        result.put("dateQueryItemDto", dateQueryItemDto);
        return result;
    }

    @Override
    public PageUtils queryTblData(DataQueryReq dataQueryReq, Long tnmtid, boolean repetition) {
        try {
            if (dataQueryReq.getDateQueryItemDto() == null || dataQueryReq.getDateQueryItemDto().getDataTblid() == null) {
                throw new RRException("查询失败：请传入要查询的表");
            }
            QueryWrapper<DataFldEntity> dataFldWrapper = new QueryWrapper<>();
            dataFldWrapper.eq("Data_Tblid", dataQueryReq.getDateQueryItemDto().getDataTblid());
            dataFldWrapper.orderByAsc("Fld_Ord");
            List<DataFldEntity> dataFldEntityList = dataFldService.list(dataFldWrapper);

            DataTblEntity dataTblEntity = dataTblService.getById(dataQueryReq.getDateQueryItemDto().getDataTblid());

            if (dataFldEntityList == null || dataFldEntityList.isEmpty()) {
                throw new RRException("查询失败：数据库不存在该表的字段");
            }

            Map<String, Object> dbEntity = dbService.queryDBByCd(tnmtid, dataQueryReq.getDataType(), dataQueryReq.getDateQueryItemDto().getDbid());
            StringBuffer dataCountBuf = new StringBuffer(); //查询总数
            StringBuffer dataEntity = new StringBuffer(); //查询结果

            //限定查询的分区

            String dpSql = "";
            if(dataQueryReq.getDpid() != null){
                DpEntity dp = dpDao.selectById(dataQueryReq.getDpid());
                dpSql = StorageUtils.jointDp(dbEntity.get("dbUsageCd").toString(), dp.getDpDt());
            }

            dataCountBuf.append("select count(*) as count");
            dataEntity.append("select ");
            for (DataFldEntity fldEntity : dataFldEntityList) {
                dataEntity.append(fldEntity.getFldPhysNm());
                dataEntity.append(",");
            }
            dataEntity.deleteCharAt(dataEntity.lastIndexOf(","));
            dataEntity.append(" from (select row_number() over (order by ");
            dataEntity.append(dataFldEntityList.get(0).getFldPhysNm());
            dataEntity.append(") as rnum,");
            for (DataFldEntity fldEntity : dataFldEntityList) {
                dataEntity.append(fldEntity.getFldPhysNm());
                dataEntity.append(",");
            }
            dataEntity.deleteCharAt(dataEntity.lastIndexOf(","));

            dataEntity.append(" from ");
            dataCountBuf.append(" from ");

            dataEntity.append(dbEntity.get("dbPhysNm").toString() + "." + dataTblEntity.getDataTblPhysNm());
            dataCountBuf.append(dbEntity.get("dbPhysNm").toString() + "." + dataTblEntity.getDataTblPhysNm());

            dataEntity.append(" where 1 = 1 ");
            dataCountBuf.append(" where 1 = 1 ");

            if (listNoEmpty(dataQueryReq.getDataQueryCondEntityList())) {
                for (UsrDataQueryCondEntity condEntity : dataQueryReq.getDataQueryCondEntityList()) {
                    for (DataFldEntity fldEntity : dataFldEntityList) {
                        if (condEntity.getFldid().equals(fldEntity.getFldid())) {

                            dataEntity.append("and ");
                            dataCountBuf.append("and ");

                            dataEntity.append(fldEntity.getFldPhysNm() + " ");
                            dataCountBuf.append(fldEntity.getFldPhysNm() + " ");

                            dataEntity.append(condEntity.getOprSmb() + " ");
                            dataCountBuf.append(condEntity.getOprSmb() + " ");

                            if (condEntity.getOprSmb().contains("like")) {
                                dataEntity.append("'%" + condEntity.getOperQty() + "%' ");
                                dataCountBuf.append("'%" + condEntity.getOperQty() + "%' ");
                            } else {
                                //除了int类型 其他类型加上单引号
                                if ("bigint".contains(fldEntity.getFldDataType()) || fldEntity.getFldDataType().startsWith("decimal")) {

                                    dataEntity.append(condEntity.getOperQty() + " ");
                                    dataCountBuf.append(condEntity.getOperQty() + " ");
                                } else {

                                    dataEntity.append("'" + condEntity.getOperQty() + "' ");
                                    dataCountBuf.append("'" + condEntity.getOperQty() + "' ");
                                }
                            }
                        }
                    }
                }
            }
            if(StringUtils.isNotBlank(dpSql)){
                dataEntity.append(" and " + dpSql);//限定查询的分区
                dataCountBuf.append(" and " + dpSql);//限定查询的分区
            }

            dataEntity.append(") t");
            dataEntity.append(" where rnum between ");
            dataEntity.append(dataQueryReq.getStart() + 1); //这里跟mysql的limit有差别
            dataEntity.append(" and ");
            dataEntity.append(dataQueryReq.getStart() + dataQueryReq.getLimit());

            Integer count = dataQueryReq.getTotal();
            if (count.intValue() == 0 || dataQueryReq.getCurPage().intValue() == 1) {
                logger.info("查询总数sql={}", dataCountBuf.toString());
                count = Integer.parseInt(hiveService.selectData(dataCountBuf.toString()).get(0).get("count") + "");
            }
            logger.info("查询分页sql={}", dataEntity.toString());
            List<Map<String, Object>> list = hiveService.selectData(dataEntity.toString());
            return new PageUtils(list, count, dataQueryReq.getLimit(), dataQueryReq.getCurPage());
        } catch (RRException re) {
            throw re;
        } catch (Exception e) {
            e.printStackTrace();
            if (StringUtils.isEmpty(e.getMessage())) {
                throw new RRException("查询无数据", e);
            }
            if (e.getMessage().contains("Unknown column")) {
                throw new RRException("查询失败：该表字段存在错误", e);
            }
            if (e.getMessage().contains("Could not resolve table reference:") || e.getMessage().contains("Table not found")) {

                //查询不到表时首先执行同步指令再查询一下
                if(!repetition){
                    StringBuilder sb = new StringBuilder(100);
                    sb.append("sh ");
                    String shRefresh = systemParams.getShRefresh();
                    sb.append(shRefresh);
                    logger.info("执行同步数据表命令:{}", shRefresh);
                    try {
                        SSH.execScript(sb.toString(), systemParams);
                    }catch (Exception ee){
                        logger.error("执行同步数据表命令:{}异常：{}", shRefresh, ee);
                    }
                }

                if ("03".equals(dataQueryReq.getDataType())) {
                    throw new RRException("查询失败：该表未清洗！", e);
                }
                if ("06".equals(dataQueryReq.getDataType())) {
                    throw new RRException("查询失败：该表未脱敏！", e);
                }
                throw new RRException("查询失败：数据库中不存在该表！", e);
            }
            if (e.getMessage().contains("SELECT command denied to user")) {
                throw new RRException("查询失败：用户没有访问数据库权限", e);
            }
            throw new RRException("查询无数据", e);
        }
    }

    private boolean listNoEmpty(List<?> list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }
}