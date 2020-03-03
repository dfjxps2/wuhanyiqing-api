package io.dfjinxin.modules.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.common.utils.Query;
import io.dfjinxin.modules.tag.dao.TagInfoDao;
import io.dfjinxin.modules.tag.entity.TagInfoEntity;
import io.dfjinxin.modules.tag.service.TagInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("tagInfoService")
public class TagInfoServiceImpl extends ServiceImpl<TagInfoDao, TagInfoEntity> implements TagInfoService {

    private static Logger LOG = LoggerFactory.getLogger(TagInfoServiceImpl.class);

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TagInfoEntity> page = this.page(
                new Query<TagInfoEntity>().getPage(params),
                new QueryWrapper<TagInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<TagInfoEntity> queryFldTagInfoList(Long tnmtid) {
        return baseMapper.queryFldTagInfoList(tnmtid);
    }

    @Override
    public List<TagInfoEntity> queryTblTagInfoList(Long tnmtid) {
        return baseMapper.queryTblTagInfoList(tnmtid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAndGetId(TagInfoEntity entity) {

//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("Label_Nm", entity.getLabelNm());
//        wrapper.eq("Label_Catgid", entity.getLabelCatgid());
//        wrapper.eq("Tnmtid", entity.getTnmtid());

        if (baseMapper.selectCount(getQueryWrapper(entity)).intValue() > 0) {
            throw new RRException(entity.getLabelNm() + ",标签已存在!");
        }
        baseMapper.saveAndGetId(entity);
        return entity.getLabelid();
    }

    @Override
    public List<TagInfoEntity> queryRuleInfoList(Long tenantId) {
        return baseMapper.queryRuleInfoList(tenantId);
    }

    @Override
    public TagInfoEntity selectEntityOne(TagInfoEntity entity) {
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("Label_Nm", entity.getLabelNm());
//        wrapper.eq("Label_Catgid", entity.getLabelCatgid());
//        wrapper.eq("Tnmtid", entity.getTnmtid());
        return baseMapper.selectOne(getQueryWrapper(entity));
    }

    @Override
    public TagInfoEntity selectEntityOneById(Integer labelId) {
        return baseMapper.selectById(labelId);
    }

    private QueryWrapper getQueryWrapper(TagInfoEntity entity) {
        if (entity != null) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("Label_Nm", entity.getLabelNm());
            wrapper.eq("Label_Catgid", entity.getLabelCatgid());
            wrapper.eq("Tnmtid", entity.getTnmtid());
            return wrapper;
        }
        return null;
    }

}