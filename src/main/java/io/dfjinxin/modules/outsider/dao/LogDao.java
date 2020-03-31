package io.dfjinxin.modules.outsider.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.dfjinxin.modules.outsider.entity.LeavePerson;
import io.dfjinxin.modules.outsider.entity.LogEntity;

@Mapper
public interface LogDao extends BaseMapper<LogEntity>{

}
