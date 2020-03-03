package io.dfjinxin.modules.storage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dfjinxin.modules.storage.entity.SelectedTagEntity;
import io.dfjinxin.modules.storage.tree.EntityTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 14:26:46
 */
@Mapper
@Repository
public interface SelectedTagDao extends BaseMapper<SelectedTagEntity> {

    /**
     * 通过以下参数获取构造树形结构的数据
     * @Param("catgyList") 标签的目录id
     * @Param("leafList")  叶子节点的id
     * @Param("fldLabelList")
     * @Param("partId") 分区id
     * @Param("tnmtId") 租户id
     */
    List<EntityTree> getDataForEl(Map<String, Object> params);

    /**
     * 通过以下参数获取构造树形结构的数据
     * @Param("catgyList") 标签的目录id
     * @Param("leafList") 叶子节点的id
     * @Param("partId") 分区id
     * @Param("tnmtId") 租户id
     */
    List<EntityTree> getDataForSelect(Map<String, Object> params);

    /**
     * 通过以下参数获取构造树形结构的数据
     * @Param("catgyList") 标签的目录id
     * @Param("leafList") 叶子节点的id
     * @Param("partId") 分区id
     * @Param("tnmtId") 租户id
     */
    List<EntityTree> getDataForElAndCount(Map<String, Object> params);

    List<Map<String, String>> getDetailCount(@Param("leafList") ArrayList<Object> leafList,
                                             @Param("partId") Integer partId,
                                             @Param("dbId") String dbId,
                                             @Param("tnmtId")long tnmtid);


    List<EntityTree> queryFieldViewDataForEl(@Param("catgyList") List<Object> catgyList,
                                             @Param("leafList") List<Object> leafList,
                                             @Param("fldLabelList") List<Object> fldLabelList,
                                             @Param("partId") Integer partId,
                                             @Param("dbId") Integer dbId,
                                             @Param("tnmtId") long tnmtid);
}
