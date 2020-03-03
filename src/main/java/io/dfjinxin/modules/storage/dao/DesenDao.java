package io.dfjinxin.modules.storage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.storage.dto.DesenData;
import io.dfjinxin.modules.storage.dto.DesenFld;
import io.dfjinxin.modules.storage.dto.DesenForm;
import io.dfjinxin.modules.storage.entity.DataDstzEntity;
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
public interface DesenDao extends BaseMapper<DataDstzEntity> {

    Integer queryDesenDataCount(@Param("param") DesenForm desenForm);

    List<DataTblEntity> queryDesenDataList(@Param("param") DesenForm desenForm);

    List<DesenFld> queryFldByTbId(Integer tbId);

    void deleteDataFldWashProj(Integer tbId);

    void deletePrdDataFldWashProj(Integer tbId);

}
