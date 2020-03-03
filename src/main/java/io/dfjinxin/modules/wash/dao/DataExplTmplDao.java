package io.dfjinxin.modules.wash.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.wash.entity.DataExplTmplEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据探查模板
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Repository
@Mapper
public interface DataExplTmplDao extends BaseMapper<DataExplTmplEntity> {
	/**
	 * 查询模板
	 * @return
	 */
	IPage<Map<String, Object>> selectByExplId(Page page, @Param("p") Map<String, Object> p);

	/**
	 * 查询模板
	 * @return
	 */
	List<Map<String,Object>> queryProj();

	/**
	 * 查询模板byid
	 * @return
	 */
	List<Map<String,Object>> getExplById(Map<String, Object> params);
}
