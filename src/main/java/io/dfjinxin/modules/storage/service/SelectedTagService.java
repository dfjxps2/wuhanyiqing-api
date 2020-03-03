package io.dfjinxin.modules.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.storage.entity.SelectedTagEntity;
import io.dfjinxin.modules.storage.tree.EntityTree;

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
public interface SelectedTagService extends IService<SelectedTagEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<EntityTree> getTreeData(Map<String, Object> params);

    List<EntityTree> getSelectTreeData(Map<String, Object> params);

    List<Map<String,String>> getDetailCount(ArrayList<Object> leafList,Integer partId,String dbId,long tnmtId);

    List<EntityTree> getFieldViewTreeData(ArrayList<Object> catgyList, ArrayList<Object> leafList, ArrayList<Object> fldLabelList, Integer partId,Integer dbId, long tnmtid);
}

