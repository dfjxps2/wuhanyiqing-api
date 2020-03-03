package io.dfjinxin.modules.meta.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.meta.entity.DpEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据片
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@Repository
@Mapper
public interface DpDao extends BaseMapper<DpEntity> {
    IPage<Map<String, Object>> queryAll(Page page, Map<String, Object> m);

    List<DpEntity> queryDpEntityById(Integer dpid);
}
