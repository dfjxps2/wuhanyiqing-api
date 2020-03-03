package io.dfjinxin.modules.invest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.invest.entity.DataInvestEntity;
import io.dfjinxin.modules.invest.entity.TableFldEntity;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GaoPh on 2019/6/18.
 */
@Repository
@Mapper
public interface DataInvestDao extends BaseMapper<DataInvestEntity> {
    IPage<DataInvestEntity> queryInvestList(@Param("page") Page page, @Param("tps") Map<String, Object> m);

    List<Map<String, Object>> queryResources();
    //表的字段信息
    IPage<Map<String, Object>> queryTblFld(@Param("page") Page page,@Param("tps") Map<String, Object> tps);
    //字段对应的模板信息
    List<Map<String,Object>> queryTempl();

    //获取一个表的所有字段名称
    List<Map<String, Object>> getTblFlds(@Param("tid") Map<String, Object> tid);
    //表字段保存
    void saveFlds(@Param("fld") Map<String, Object> fld);


    //表字段批量保存
    void saveTableFlds(@Param("list") List<Integer> list,@Param("fldKey") Integer key,@Param("fldNull") Integer fn);
    //表字段模板保存--删除目前字段的模板信息
    void deleFldsTmpl(@Param("entity") Integer map );
    //表字段模板保存--需要插入的新数据
    List<Map<String, Object>> getTmpls(@Param("entity") Map<String, Object> map);
    //表字段模板保存--更新字段模板信息
    void insertFldTmpl(@Param("list") List<Map<String, Object>> list);

    //得到需要匹配模板的的表字段
    List getFieldByIds(@Param("tablIds") List<Integer> tblIds);
    //得到字段模板匹配规则
    List getMarkRules(@Param("tenId")Map<String,Object> tenId);

    //表字段规则批量保存
    void saveFldsRules(@Param("list") List<String> list,@Param("dataExplTmplid") Integer tmpl);

    //表信息保存
    void saveTables(@Param("entity") DataInvestEntity dit);

    //表信息批量保存
    void batchSaveTables(@Param("list") List<String> list,@Param("dataSrcId") Integer srcid,@Param("incrOrFull") String icf);

    //根据字段模板获取清洗运算规则
     List<Integer> getCmpuIds(@Param("entity")Integer  parm);

     //字段清洗规则批量插入 数据字段清洗项目表
     void insertFldWashProj(@Param("list") List<Map<String, Object>> list);
    //获取字段所有模板信息
    List<Map<String, Object>> getExplTempls(@Param("tem") Map<String, Object> tem);

    //获取表的规则信息
    List<Map<String, Object>> getTblRule(@Param("tbl") Map<String, Object> tbl);

    //获取清洗规则
    List<Map<String, Object>> getWashRules();

    //删除指定字段的清洗运算ID
    void deleteFldCumId(@Param("entity")Integer  parm);

    //得到指定字段的规则信息
    List<Map<String, Object>> getFldRules(@Param("list") List<String> list);

    //字段规则更新
     void delFldRule(@Param("entity") Map<String,Object> rs);

     //字段规则新增
    void insertFldRule(@Param("entity") Map<String,Object> rs);

    //查询作业表信息
    Map<String, Object> getJobStatus();

    //获取租户可查看数据库信息
    List<Map<String, Object>> getDbDatas(@Param("param") Map<String,Object> map);

}
