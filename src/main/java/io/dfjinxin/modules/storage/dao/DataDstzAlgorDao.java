package io.dfjinxin.modules.storage.dao;

import io.dfjinxin.modules.storage.entity.DataDstzAlgorEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 数据脱敏算法
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
@Repository
@Mapper
public interface DataDstzAlgorDao extends BaseMapper<DataDstzAlgorEntity> {
	
}
