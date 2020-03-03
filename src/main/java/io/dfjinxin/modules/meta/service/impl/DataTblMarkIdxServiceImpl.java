package io.dfjinxin.modules.meta.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.tag.entity.TagRuleEntity;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;
import io.dfjinxin.modules.tag.service.TagTypeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;

import io.dfjinxin.modules.meta.dao.DataTblMarkIdxDao;
import io.dfjinxin.modules.meta.entity.DataTblMarkIdxEntity;
import io.dfjinxin.modules.meta.service.DataTblMarkIdxService;
import org.springframework.transaction.annotation.Transactional;


@Service("dataTblMarkIdxService")
public class DataTblMarkIdxServiceImpl extends ServiceImpl<DataTblMarkIdxDao, DataTblMarkIdxEntity> implements DataTblMarkIdxService {

    @Autowired
    private TagTypeService tagTypeService;


    private Logger log = LoggerFactory.getLogger(DataFldMarkIdxServiceImpl.class);

    /**
     * 分页
     *
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataTblMarkIdxEntity> page = this.page(
                new Query<DataTblMarkIdxEntity>().getPage(params),
                new QueryWrapper<DataTblMarkIdxEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 列表展示
     *
     * @return
     */
    @Override
    public PageUtils queryDataTblMarkIdx(Map<String, Object> params) {
        long no = params.containsKey("page") ? Long.valueOf(params.get("page").toString()) : 1;
        long limit = params.containsKey("limit") ? Long.valueOf(params.get("limit").toString()) : 10;
        Page pager = new Page(no, limit);
        IPage<Map<String, Object>> pageData = baseMapper.queryDataTblMarkIdx(pager, params);
        //getRecords 调方法
        List<Map<String, Object>> list = pageData.getRecords();
        for (Map<String, Object> objectMap : list) {
            List<Map<String, Object>> labelList = new ArrayList<>();
            // Object 转化为String类型的数据,把对应的key取出来
            String label = objectMap.get("label") + "";
            if (label != null && label != "") {
                String[] labelArr = label.split(",");
                for (String arr : labelArr) {
                    HashMap<String, Object> labelTempMap = new HashMap<>();
                    if (Strings.isNullOrEmpty(arr)) {
                        continue;
                    }
                    String[] split = arr.split(":");
                    if (split.length > 3) {     //split的长度是2
                        labelTempMap.put("Label_Nm", split[0]);
                        labelTempMap.put("Labelid", split[1]);
                        labelTempMap.put("Label_Catgid", split[2]);
                        labelTempMap.put("Label_Catg_Nm", split[3]);
                        labelList.add(labelTempMap);
                    } else if (split.length > 1) {
                        labelTempMap.put("labelName", split[0]);
                        labelList.add(labelTempMap);
                    } else {
                        //do nothing
                    }
                }
            }
            objectMap.put("label", labelList);
        }
        return new PageUtils(pageData);
    }


    /**
     * 批量添加表和标签
     *
     * @param tblList
     * @param labelList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSaveTblLabel(List<Integer> tblList, List<Integer> labelList) {
        for (Integer dataTbl : tblList) {

            QueryWrapper<DataTblMarkIdxEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("Data_Tblid", dataTbl);
            List<DataTblMarkIdxEntity> entityList = baseMapper.selectList(wrapper);

            List<Integer> dataTableList = new ArrayList<>();
            for (DataTblMarkIdxEntity entity : entityList) {
                dataTableList.add(entity.getLabelid());
            }

            List<Integer> labList = (List) CollectionUtils.subtract(labelList, dataTableList);
            if (labList.size() > 0) {
                baseMapper.batchSaveTblLabel(dataTbl, labList);
            }
        }
    }

    /**
     * 批量删除表标签
     *
     * @param tblList
     * @param labelList
     */
    @Override
    public void batchDeleteTblLabel(List<Integer> tblList, List<Integer> labelList) {
        for (Integer dataTbl : tblList) {
            try {
                baseMapper.batchDeleteTblLabel(dataTbl, labelList);
            } catch (RRException e) {
                log.error(e.toString());
            }
        }
    }

    @Override
    public Integer selectCountByLabelId(Integer labelId) {
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("Labelid", labelId);
//        return baseMapper.selectCount(wrapper);
        return baseMapper.selectLabelUsedTimesByTable(labelId);
    }

    @Override
    public Integer selectTblLabCountByLabelIds(List<Integer> labelIdList) {
        if (labelIdList != null && labelIdList.size() > 0) {
            return baseMapper.selectLabelUsedTimesByLabelIds(labelIdList);
        }
        return 0;
    }

}
