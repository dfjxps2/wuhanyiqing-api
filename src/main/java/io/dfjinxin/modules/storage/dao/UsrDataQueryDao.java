package io.dfjinxin.modules.storage.dao;

import io.dfjinxin.modules.storage.entity.UsrDataQueryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据查询
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:17:04
 */
@Repository
@Mapper
public interface UsrDataQueryDao extends BaseMapper<UsrDataQueryEntity> {

    UsrDataQueryEntity selectByUserAndName(@Param("userId")String userId,@Param("name")String name);
	
}
