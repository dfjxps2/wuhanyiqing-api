package io.dfjinxin.modules.tag.dao;

import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.entity.DataTblEntity;
import io.dfjinxin.modules.tag.entity.AutoMarkIdxRule;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagMarkDao {

    @Select("<script>" +
            "select * from label_catg " +
            "<if test='tnmtid!=null'> where tnmtid = #{tnmtid} </if>" +
            "</script>")
    List<TagTypeEntity> getAllTagTypes(@Param("tnmtid") Integer tnmtid);

    @Select("<script>" +
            "select * from label where Label_Catgid in " +
            "<foreach item='item' index='index' collection='tagCatgids' open='(' separator=',' close=')'> " +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<TagInfoEntity> getAllTagInfoByTagCatg(List<Integer> tagCatgids);

    @Select("<script>" +
            "select amir.*,l.label_nm from label l,auto_mark_idx_rule amir where l.Label_Catgid in " +
            "<foreach item='item' index='index' collection='tagCatgids' open='(' separator=',' close=')'> " +
            " #{item} " +
            "</foreach>  and l.Labelid = amir.Labelid " +
            "</script>")
    List<AutoMarkIdxRule> getAllTagRules(@Param("tagCatgids") List<Integer> tagCatgids);

    @Select("<script>"+
            "select dt.* from data_part dp " +
            "left join db db on dp.partid = db.partid <if test='tnmtid!=null'> and dp.tnmtid = #{tnmtid} </if>  " +
            "left join data_tbl dt on db.dbid = dt.dbid " +
            "where dt.Data_Tblid is not null" +
            "</script>")
    List<DataTblEntity> getTableInfos(@Param("tnmtid") Integer tnmtid);

    @Delete("delete from data_tbl_mark_idx where Labelid = #{labelId} and Data_Tblid = #{tableId}")
    void removeLabelTableAssign(@Param("labelId") Integer labelId,@Param("tableId") Integer tableId);

    @Insert("insert into data_tbl_mark_idx (Data_Tblid,Labelid,Mark_Idx_Type) values " +
            "(#{tableId},#{labelId},#{markIdxType})")
    void saveLabelTableAssign(@Param("labelId") Integer labelId,@Param("tableId")  Integer tableId,@Param("markIdxType") Integer markIdxType);

    @Select("<script>" +
            "select * from data_tbl where data_tblid in " +
            "<foreach item='item' index='index' collection='tableIds' open='(' separator=',' close=')'> " +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<DataTblEntity> getTableInfosByIds(@Param("tableIds") List<String> tableIds);

    @Select("<script>" +
            "select * from data_fld where Fldid in " +
            "<foreach item='item' index='index' collection='markIds' open='(' separator=',' close=')'> " +
            " #{item} " +
            "</foreach>" +
            "</script>")
    List<DataFldEntity> getFieldInfosByIds(@Param("markIds") List<String> markIds);

    @Delete("delete from data_fld_mark_idx where Labelid = #{labelId} and Fldid = #{markId}")
    void removeLabelFldAssign(@Param("labelId") Integer labelId,@Param("markId") Integer markId);

    @Insert("insert into data_fld_mark_idx (Fldid,Labelid,Mark_Idx_Type) values " +
            "(#{markId},#{labelId},#{markIdxType})")
    void saveLabelFldAssign(@Param("labelId") Integer labelId,@Param("markId") Integer markId,@Param("markIdxType") Integer markIdxType);
}
