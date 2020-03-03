package io.dfjinxin.modules.infoInto.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.dfjinxin.modules.infoInto.entity.T01DetainedPersonInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.support.PagedListHolder;

import java.util.List;
import java.util.Map;

/**
 * 滞汉外地人明细
 *
 * @author z.h.c
 * @email z.h.c@126.com
 * @date 2020-03-03 15:21:07
 */
@Mapper
public interface T01DetainedPersonInfoDao extends BaseMapper<T01DetainedPersonInfoEntity> {


    //    @Select("<script>\n" +
//            "select info.*\n" +
//            "from t01_detained_person_info info\n" +
//            "where 1=1 \n" +
//            "<if test='param.submitDate != null'> " +
//            "  and submit_date = {param.submitDate}" +
//            "</if>" +
//            "<if test='param.keepStatusCd != null'> " +
//            "  and keep_status_cd = {param.keepStatusCd}" +
//            "</if>" +
//            "</script>")
    IPage<T01DetainedPersonInfoEntity> queryPage(Page page, @Param("param") Map map);
}
