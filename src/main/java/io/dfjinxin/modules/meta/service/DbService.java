package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DbEntity;
import io.dfjinxin.modules.storage.dto.DateQueryItemDto;
import io.dfjinxin.modules.tag.entity.TagDataTblEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
public interface DbService extends IService<DbEntity> {

    PageUtils queryPage(Map<String, Object> params);

//    List<DbEntity> queryDbListByTnmt(Long tenantId);

    List<DbEntity> queryDBListByDbid(List<TagDataTblEntity> tableList);

    List<DbEntity> queryDBListByPartid(Integer partid);

    Map<String, Object> queryDBByCd(Long tnmtid, String dataType, Integer dbid);

    DateQueryItemDto queryDBUBytblid(Integer tblid);
}

