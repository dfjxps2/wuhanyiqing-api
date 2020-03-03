package io.dfjinxin.modules.meta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.meta.dao.DataFldMarkIdxDao;
import io.dfjinxin.modules.meta.entity.DataFldMarkIdxEntity;
import io.dfjinxin.modules.meta.service.DataFldMarkIdxService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("dataFldMarkIdxService")
public class DataFldMarkIdxServiceImpl extends ServiceImpl<DataFldMarkIdxDao, DataFldMarkIdxEntity> implements DataFldMarkIdxService {

    private Logger log = LoggerFactory.getLogger(DataFldMarkIdxServiceImpl.class);


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataFldMarkIdxEntity> page = this.page(
                new Query<DataFldMarkIdxEntity>().getPage(params),
                new QueryWrapper<DataFldMarkIdxEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAddFldLabel(List<Integer> fldList, List<Integer> labelList) {

        for (Integer fldId : fldList) {

            QueryWrapper<DataFldMarkIdxEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("Fldid", fldId);
            List<DataFldMarkIdxEntity> entityList = baseMapper.selectList(wrapper);

            List<Integer> fldLabelList = new ArrayList<>();
            for (DataFldMarkIdxEntity entity : entityList) {
                fldLabelList.add(entity.getLabelid());
            }

            List<Integer> labList = (List) CollectionUtils.subtract(labelList, fldLabelList);
            if (labList.size() > 0) {
                baseMapper.batchAddFldLabel(fldId, labList);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelFldLabel(List<Integer> fldList, List<Integer> labelList) {
        for (Integer fldId : fldList) {
            try {
                baseMapper.batchDelFldLabel(fldId, labelList);
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
        return baseMapper.selectLabelUsedTimesByFld(labelId);
    }

    @Override
    public Integer selectFldLabCountByLabelIds(List<Integer> labelIdList) {
        if (labelIdList != null && labelIdList.size() > 0) {
            return baseMapper.selectLabelUsedTimesByLabelIds(labelIdList);
        }
        return 0;
    }

}