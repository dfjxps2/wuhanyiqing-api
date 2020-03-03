package io.dfjinxin.modules.meta.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.dto.DataTblReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataTblDao extends BaseMapper<DataTblEntity> {

    IPage<Map<String, Object>> queryPageByRecord(Page pager, @Param("record")DataTblReq dataTblReq);

    Integer queryCountByRecord(@Param("record")DataTblReq dataTblReq);
    List<Map<String,String>> listByParams(Map<String,Object> params);
    int listByParamsCount(Map<String,Object> params);


    long getTableCount(Map<String,Object> params);

    long getFiledCount(Map<String,Object> params);

    String getRecordCount(Map<String,Object> params);

    long getTableTagCount(Map<String,Object> params);

    long getFiledTagCount(Map<String,Object> params);

    List<Map<String,String>> getTableTagList(@Param("partId")Integer partId,
                                             @Param("dbId") String dbId,
                                             @Param("tableName") String tableName,
                                             @Param("tnmtId")long tnmtId);

    List<Map<String,String>> getFiledTagList(@Param("partId")Integer partId,
                                             @Param("dbId") String dbId,
                                             @Param("tableName") String tableName,
                                             @Param("tnmtId")long tnmtId);

    List<Map<String,String>> getTableRecordList(@Param("partId")Integer partId,
                                                @Param("dbId") String dbId,
                                                @Param("tableName") String tableName,
                                                @Param("tnmtId")long tnmtId);

    IPage<Map<String, Object>> getTableTagListDetail(Page pager,@Param("params") Map<String,Object> params);

    IPage<Map<String, Object>> getFiledTagListDetail(Page pager,@Param("params") Map<String,Object> params);

    IPage<Map<String, Object>> getTableRecordListDetail(Page pager,@Param("params") Map<String,Object> params);


    List<Map<String,String>> getTableTagListJoinLabel(@Param("partId")Integer partId,
                                                      @Param("dbId") String dbId,
                                                      @Param("labelIdList")ArrayList<Object> labelIdList,
                                                      @Param("tnmtId")long tnmtId);

    List<Map<String,String>> getFiledTagListJoinLabel(@Param("partId")Integer partId,
                                                      @Param("dbId") String dbId,
                                                      @Param("labelIdList")ArrayList<Object> labelIdList,
                                                      @Param("tnmtId")long tnmtId);

    List<Map<String,String>> getTableRecordListJoinLabel(@Param("partId")Integer partId,
                                                         @Param("dbId") String dbId,
                                                         @Param("labelIdList")ArrayList<Object> labelIdList,
                                                         @Param("tnmtId")long tnmtId);

    IPage<Map<String, Object>> getTableTagListJoinLabelDetail(Page pager,@Param("params") Map<String,Object> params);

    IPage<Map<String, Object>> getFiledTagListJoinLabelDetail(Page pager,@Param("params") Map<String,Object> params);

    IPage<Map<String, Object>> getTableRecordListJoinLabelDetail(Page pager,@Param("params") Map<String,Object> params);
}
