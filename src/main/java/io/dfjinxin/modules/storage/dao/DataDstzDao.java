package io.dfjinxin.modules.storage.dao;

import io.dfjinxin.modules.storage.dto.DesenData;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.storage.entity.DataDstzEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据脱敏
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
@Repository
@Mapper
public interface DataDstzDao extends BaseMapper<DataDstzEntity> {

}
