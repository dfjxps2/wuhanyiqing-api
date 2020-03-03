package io.dfjinxin.modules.meta.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.storage.dto.DataTblReq;
import io.dfjinxin.modules.storage.dto.DesenForm;

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
public interface DataTblService extends IService<DataTblEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByRecord(DataTblReq dataTblReq, Long tnmtid);

    PageUtils listByParams(Map<String,Object> params);

    List<Map<String,String>> getCount(Map<String,Object> params);

    List<Map<String,String>> getTagDataList(Integer type, Integer partId,String dbId,String tableName,long tnmtId);

    //List<Map<String,String>> getDetailByTableRow(Integer type,Integer labelId,Integer partId,String tableName,String dbId);
    PageUtils getDetailByTableRow(Map<String,Object> params);

    List<Map<String,String>> getTagDataListJoinLabel(Integer type, Integer partId,String dbId, ArrayList<Object> labelIdList,long tnmtId);

}

