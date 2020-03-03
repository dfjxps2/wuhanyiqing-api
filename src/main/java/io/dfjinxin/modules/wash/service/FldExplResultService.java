package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.FldExplResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 字段探查结果
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
public interface FldExplResultService extends IService<FldExplResultEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils expReport(Map<String, Object> params);

    List<Map<String,Object>> expInfo(Map<String, Object> params);

    List<Map<String,Object>> expProj(Map<String, Object> params);

    void download(HttpServletRequest request, HttpServletResponse response) throws Exception;
}

