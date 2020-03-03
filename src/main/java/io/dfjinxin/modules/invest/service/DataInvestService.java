package io.dfjinxin.modules.invest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.invest.entity.DataInvestEntity;
import io.dfjinxin.modules.invest.entity.TableFldEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by GaoPh on 2019/6/18.
 */

public interface DataInvestService extends IService<DataInvestEntity> {

    PageUtils queryInvestList(Map<String, Object> params);

    List<Map<String, Object>> queryResources();

    //表的字段信息
    PageUtils queryTblFld(Map<String, Object> tps);

    //获取一个表的所有字段名称
    List<Map<String, Object>> getTblFlds( Map<String, Object> tps);

    //表字段模板保存
    void saveFldTmpls(List<Map<String, Object>> list);

    //得到指定字段的规则信息
    List<Map<String, Object>> getFldRules( List<String> list);

    //表字段批量保存
    void batchSaveFlds(List<Integer> list,Integer key,Integer fn,List<Integer> tmpl);

    //表信息保存
    void saveTables( DataInvestEntity dit);

    //表信息批量保存
    void batchSaveTables( List<String> list, Integer srcid,String icf);

    //获取字段所以模板信息
    List<Map<String, Object>> getExplTempls(Map<String, Object> tem);

    ///获取表的规则信息
    List<Map<String, Object>> getTblRule( Map<String, Object> tbl);

    //获取清洗规则
    List<Map<String, Object>> getWashRules();

    //自动匹配规则
     R fieldAutoMark( List<Integer> tableIds,Map<String, Object> tenantId);

    //元数据更新
    R execUpdate(Map<String, Object> params);

   //获取元数据更新状态
   Map<String, Object> getJobStatus();

   //获取数据库信息
    List<Map<String, Object>> queryDb( Map<String, Object> map);

}
