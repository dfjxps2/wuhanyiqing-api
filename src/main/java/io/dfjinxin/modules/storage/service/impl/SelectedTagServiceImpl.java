package io.dfjinxin.modules.storage.service.impl;

import io.dfjinxin.modules.storage.dao.SelectedTagDao;
import io.dfjinxin.modules.storage.entity.SelectedTagEntity;
import io.dfjinxin.modules.storage.service.SelectedTagService;
import io.dfjinxin.modules.storage.tree.EntityTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;



@Service("tagInfoNewService")
public class SelectedTagServiceImpl extends ServiceImpl<SelectedTagDao, SelectedTagEntity> implements SelectedTagService {
    @Autowired
    SelectedTagDao selectedTagDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SelectedTagEntity> page = this.page(
                new Query<SelectedTagEntity>().getPage(params),
                new QueryWrapper<SelectedTagEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<EntityTree> getTreeData(Map<String, Object> params) {
        String type=(String)params.get("type");//获取查询类型
        /**
         * 获取树形下拉框的数据
         * vue 的select-tree 支持显示节点下的叶子数量， el-tree 不支持显示数量
         *
         * type:["el-tree":el-tree的数据 ，
         *       "select-tree":select-tree 的数据
         *       "el-tree-show-count":查询el-tree的数据 并获取标签下数量]
         */
        if("el-tree".equals(type)){
            return selectedTagDao.getDataForEl(params);
        }else if("select-tree".equals(type)) {
            return selectedTagDao.getDataForSelect(params);
        }else if("el-tree-show-count".equals(type)){
            return selectedTagDao.getDataForElAndCount(params);
        }else{
            return selectedTagDao.getDataForSelect(params);
        }

    }

    @Override
    public List<EntityTree> getSelectTreeData(Map<String, Object> params) {
        return selectedTagDao.getDataForSelect(params);
    }

    @Override
    public List<Map<String, String>> getDetailCount(ArrayList<Object> leafList,Integer partId,String dbId,long tnmtId) {
        return selectedTagDao.getDetailCount(leafList,partId,dbId,tnmtId);
    }

    /**
     * 字段标签管理-标签视图-左侧标签列表
     * 根据分区、标签所属表、字段查询指定标签被使用的次数
     */
    @Override
    public List<EntityTree> getFieldViewTreeData(ArrayList<Object> catgyList, ArrayList<Object> leafList, ArrayList<Object> fldLabelList, Integer partId,Integer dbId, long tnmtid) {
        return selectedTagDao.queryFieldViewDataForEl(catgyList,leafList,fldLabelList,partId,dbId,tnmtid);
    }


}