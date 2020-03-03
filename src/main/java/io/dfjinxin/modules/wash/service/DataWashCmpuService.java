package io.dfjinxin.modules.wash.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.wash.entity.DataWashCmpuEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 数据清洗运算
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
public interface DataWashCmpuService extends IService<DataWashCmpuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryList(Map<String, Object> params);

    int getInfo(String name,Integer id,long tnmtid);

    void saveInfo(DataWashCmpuEntity dataWashCmpuEntity) throws IOException, InterruptedException;

    void updateInfo(DataWashCmpuEntity dataWashCmpu) throws IOException, InterruptedException;

    List<DataWashCmpuEntity> queryDataWashCmpuByDataWashCmpuType(Integer dataWashCmpuType);
}

