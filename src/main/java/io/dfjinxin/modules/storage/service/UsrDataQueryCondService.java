package io.dfjinxin.modules.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;

import java.util.Map;

/**
 * 用户数据查询条件
 *
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:19:07
 */
public interface UsrDataQueryCondService extends IService<UsrDataQueryCondEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

