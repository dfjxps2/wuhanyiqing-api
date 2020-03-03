package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.dto.DataTblDto;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 已发布数据处理作业
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
public interface PrdDataProcJobService extends IService<PrdDataProcJobEntity> {

    final static int HIVE_EXP_LIMIT = 10000;
    final static int DB_USAGEID_HISDB = 2;
    final static int DB_USAGEID_CLSDB = 3;
    final static int DB_USAGEID_ISUDB = 4;
    final static int DB_USAGEID_TMPDB = 5;
    final static int DB_USAGEID_DESDB = 6;

    /**
     * 根据使用用途获取hive表全名
     * @param dataTblid
     * @param dbUsageid 1汇聚库 2历史库 3清洗库 4问题库 5临时库 6脱敏库 7基础库 8主题库
     * @return
     */
    DataTblDto getHiveTable(Integer dataTblid, Integer dbUsageid, Integer jobid, Long Tnmtid);

    String getHiveSelectSql(DataTblDto dto, String where);

    String getHiveSelectSql(DataTblDto dto, String column, String where);

    String getHivePageSql(DataTblDto dto, String column, String where, Integer pageIndex, Integer pageLimit);

    String getHiveCountSql(String tbl, String where);

    PageUtils queryPage(Map<String, Object> params);

    void checkHiveException(Exception e);

    RRException checkHiveException2(Exception e, boolean isUpdateMeta);

    List<String> getPrimaryKeys(DataTblDto dto);

    List<DataFldEntity> getPrimaryFlds(DataTblDto dto);

    List<Map<String,Object>> selectDataRepeate(String sql);

    String tagConvert(Map<String, Object> m, String tags,List<DataFldEntity> fldList, List<DataWashCmpuEntity> cmpuList);

    void updateJobById(PrdDataProcJobEntity procJobEntity);

}

