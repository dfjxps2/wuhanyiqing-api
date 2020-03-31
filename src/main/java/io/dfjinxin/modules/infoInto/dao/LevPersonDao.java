package io.dfjinxin.modules.infoInto.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.infoInto.entity.LevPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/31 17:32
 * @Version: 1.0
 */
@Mapper
public interface LevPersonDao extends BaseMapper<LevPerson> {
    IPage<LevPerson> queryPage(Page page, @Param("param") Map map);
}
