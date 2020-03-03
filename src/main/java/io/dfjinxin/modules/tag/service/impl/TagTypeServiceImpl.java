package io.dfjinxin.modules.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.tag.dao.TagInfoDao;
import io.dfjinxin.modules.tag.dao.TagTypeDao;
import io.dfjinxin.modules.tag.entity.TagTypeEntity;
import io.dfjinxin.modules.tag.service.TagTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("tagTypeService")
public class TagTypeServiceImpl extends ServiceImpl<TagTypeDao, TagTypeEntity> implements TagTypeService {

    private static Logger LOG = LoggerFactory.getLogger(TagTypeServiceImpl.class);


    @Autowired
    private TagInfoDao tagInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TagTypeEntity> page = this.page(
                new Query<TagTypeEntity>().getPage(params),
                new QueryWrapper<TagTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List queryTagTypeList(Long tenantId) {
        return baseMapper.queryTagTypeList(tenantId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAndGetId(TagTypeEntity entity) {

//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("Supr_Label_Catgid", entity.getSuprLabelCatgid());
//        wrapper.eq("Label_Catg_Nm", entity.getLabelCatgNm());
//        wrapper.eq("Tnmtid", entity.getTnmtid());

        if (baseMapper.selectCount(getQueryWrapper(entity)).intValue() > 0) {
            throw new RRException(entity.getLabelCatgNm() + ",标签类目已存在!");
        }
        baseMapper.saveAndGetId(entity);
        return entity.getLabelCatgid();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTagAndLabel(List tagIdList, List labelIdList) {
        if (labelIdList != null && labelIdList.size() > 0) {
            tagInfoDao.deleteBatchIds(labelIdList);
        }
        if (tagIdList != null && tagIdList.size() > 0) {
            baseMapper.deleteBatchIds(tagIdList);
        }
    }

    @Override
    public TagTypeEntity selectEntityOne(Integer labelCatgid) {
        return baseMapper.selectById(labelCatgid);
    }

    @Override
    public TagTypeEntity selectEntityByWrapper(TagTypeEntity entity) {
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("Supr_Label_Catgid", entity.getSuprLabelCatgid());
//        wrapper.eq("Label_Catg_Nm", entity.getLabelCatgNm());
//        wrapper.eq("Tnmtid", entity.getTnmtid());
        return baseMapper.selectOne(getQueryWrapper(entity));
    }

    private QueryWrapper getQueryWrapper(TagTypeEntity entity) {
        if (entity != null) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("Supr_Label_Catgid", entity.getSuprLabelCatgid());
            wrapper.eq("Label_Catg_Nm", entity.getLabelCatgNm());
            wrapper.eq("Tnmtid", entity.getTnmtid());
            return wrapper;
        }
        return null;
    }

}