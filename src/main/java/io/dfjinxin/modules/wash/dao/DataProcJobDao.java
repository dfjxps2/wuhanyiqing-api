package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.dto.FieldRuleDto;
import io.dfjinxin.modules.wash.entity.DataProcJobEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * 数据处理作业
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Repository
@Mapper
public interface DataProcJobDao extends BaseMapper<DataProcJobEntity> {
    /**
     * 清洗任务列表
     * @return
     */
    IPage<Map<String, Object>> queryTaskList(Page page, @Param("m") Map<String, Object> m);

    /**
     * 发布任务列表
     * @return
     */
    IPage<Map<String, Object>> queryPublishList(Page page, @Param("m") Map<String, Object> m);

    /**
     * 删除失败和完成的旧作业
     * @param idList
     * @return
     */
    int deleteOldJob(@Param("ids") String[] idList);

    boolean updateDelPublish(@Param("tableid") String tableid);

    boolean updateRePublish(@Param("tableid") String tableid,@Param("scheduleType") String scheduleType);

    //获取表字段的规则信息
    IPage<FieldRuleDto> queryTblRule(Page page, @Param("tbl") Map<String, Object> tbl);

    IPage<FieldRuleDto> queryTaskRule(Page page, @Param("tbl") Map<String, Object> tbl);

    //获取清洗规则
    List<Map<String, Object>> getWashRules(@Param("m") Map<String, Object> m);
}
