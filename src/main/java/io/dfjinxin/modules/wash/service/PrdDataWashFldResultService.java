package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.wash.entity.PrdDataWashFldResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 已发布作业字段清洗结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
public interface PrdDataWashFldResultService extends IService<PrdDataWashFldResultEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<PrdDataWashFldResultEntity> queryDataByJobid(Integer jobid);

    PageUtils washReport(Map<String, Object> params);

    List<Map<String,Object>> washInfo(Map<String, Object> params);

    List<Map<String,Object>> washProj(Map<String, Object> params);

    List<Map<String,Object>> dataSrcList(Map<String, Object> params);

    List<Map<String,Object>> tableFld(Map<String, Object> params);

    R tableFldData(Map<String, Object> params);

    R comparisonData(Map<String, Object> params);

    void download(HttpServletRequest request, HttpServletResponse response) throws Exception;

    PageUtils fieldProInfo(Map<String, Object> params);

    PageUtils tableReport(Map<String, Object> params);

    PageUtils tableReportInfo(Map<String, Object> params);

    PageUtils tableProInfo(Map<String, Object> params);

    PageUtils tableProData(Map<String, Object> params);

    String lastExecDate(Map<String, Object> params);
}

