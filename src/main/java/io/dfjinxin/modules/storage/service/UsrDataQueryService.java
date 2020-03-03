package io.dfjinxin.modules.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.dto.DataQueryReq;
import io.dfjinxin.modules.storage.dto.UsrDataQueryDto;
import io.dfjinxin.modules.storage.entity.UsrDataQueryEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户数据查询
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:17:04
 */
public interface UsrDataQueryService extends IService<UsrDataQueryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<UsrDataQueryDto> queryList(String userId);

    boolean saveDataQuery(UsrDataQueryDto usrDataQueryDto, String userId);

    Map<String, Object>  dataTblFld(Integer tblid);

    PageUtils queryTblData(DataQueryReq dataQueryReq, Long tnmtid, boolean repetition);
}

