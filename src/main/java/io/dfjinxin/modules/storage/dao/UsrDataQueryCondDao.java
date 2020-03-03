package io.dfjinxin.modules.storage.dao;

import io.dfjinxin.modules.storage.entity.UsrDataQueryCondEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户数据查询条件
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:19:07
 */
@Repository
@Mapper
public interface UsrDataQueryCondDao extends BaseMapper<UsrDataQueryCondEntity> {
	
}
