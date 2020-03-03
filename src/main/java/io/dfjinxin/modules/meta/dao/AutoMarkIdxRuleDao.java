package io.dfjinxin.modules.meta.dao;

import io.dfjinxin.modules.meta.entity.AutoMarkIdxRuleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 自动标引规则
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:26:31
 */
@Repository
@Mapper
public interface AutoMarkIdxRuleDao extends BaseMapper<AutoMarkIdxRuleEntity> {
	
}
