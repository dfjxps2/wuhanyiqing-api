package io.dfjinxin.modules.meta.dao;

import io.dfjinxin.modules.meta.entity.TnmtEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 租户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:18:49
 */
@Repository
@Mapper
public interface TnmtDao extends BaseMapper<TnmtEntity> {

    @Select("SELECT tt.Tnmtid,"+
            "tt.Tnmt_Cd,"+
            "tt.Tnmt_Nm,"+
            "tt.Tnmt_Cn_Nm,"+
            "tt.ETL_SysCd,"+
            "tt.Tnmt_Type"+
            " FROM tnmt tt,department dmt where tt.Tnmtid = dmt.tnmtid and dmt.department_cd=#{departId}")
    TnmtEntity getTnmtByDepart(@Param("departId") String departId);
}
