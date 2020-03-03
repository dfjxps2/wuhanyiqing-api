package io.dfjinxin.modules.wash.dao;

import io.dfjinxin.modules.wash.entity.WashCmpuParaEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 清洗运算参数
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Repository
@Mapper
public interface WashCmpuParaDao extends BaseMapper<WashCmpuParaEntity> {
	
}
