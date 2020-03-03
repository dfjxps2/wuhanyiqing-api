package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.wash.dto.FieldRuleDto;
import io.dfjinxin.modules.wash.entity.DataProcJobEntity;
import io.dfjinxin.modules.wash.entity.PrdDataProcJobEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据处理作业
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
public interface DataProcJobService extends IService<DataProcJobEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 清洗任务列表
     * @return
     */
    PageUtils queryTaskList(Map<String, Object> params);

    /**
     * 发布任务列表
     * @return
     */
    PageUtils queryPublishList(Map<String, Object> params);

    /**
     * 执行作业
     * @param params
     * @return
     */
    R doRun(Map<String, Object> params);
    /**
     * 添加并执行作业
     */
    R newRun(Map<String, Object> params, String cmd);

    /**
     * 添加并执行探查作业
     * @param params
     * @return
     */
    R doExplorer(Map<String, Object> params);

    /**
     * 添加并执行清洗作业
     * @param params
     * @return
     */
    R doWash(Map<String, Object> params);

    /**
     * 发布作业
     * @param params
     * @return
     */
    R doPublish(Map<String, Object> params);

    R doPublishEach(PrdDataProcJobEntity entity, DataTblEntity m, Date now, Integer jobType, Integer scheduleType, String dpType);

    /**
     * 删除发布作业
     * @param params
     * @return
     */
    R delPublish(Map<String, Object> params);

    R delPublishEach(String tblid,Integer jobType, List<PrdDataProcJobEntity> jobs);
    /**
     * 添加新作业(删除对应旧作业)
     */
    R addJob(Map<String, Object> params);

    R addPrdJob(Map<String, Object> params);

    R execSSH(String cmd);

    /**
     * 查询字段清洗项目
     * @param tbl
     * @return
     */
    PageUtils queryTblRule(Map<String, Object> tbl);

    PageUtils queryTaskRule(Map<String, Object> tbl);

    /**
     * 保存字段清洗项目
     * @param list
     * @return
     */
    R saveTblRule(List<FieldRuleDto> list);

    /**
     * 获取清洗规则
     */
    List<Map<String, Object>> getWashRules(Map<String, Object> m);

    /**
     * 调用清洗脚本
     * @param ids  发布作业jobid数组
     * @param jobType 脚本类型 1-清洗 2-脱敏
     * @param flag 执行类型 0-发布 1-删除
     * @return
     */
    R buildPublishScript(List<String> ids, Integer jobType, String flag);

    /**
     * 调用数据脱敏脚本
     * @param jobId  发布作业jobid数组
     * @param startDate  数据开始时间
     * @param endDate  数据结束时间
     * @return
     */
    R buildTmScript(Integer jobId, String startDate, String endDate);
}

