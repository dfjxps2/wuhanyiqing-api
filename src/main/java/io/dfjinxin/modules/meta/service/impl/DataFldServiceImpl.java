package io.dfjinxin.modules.meta.service.impl;

import io.dfjinxin.modules.meta.entity.DataFldEntityTemp;
import io.dfjinxin.modules.tag.service.TagInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

import io.dfjinxin.modules.meta.dao.DataFldDao;
import io.dfjinxin.modules.meta.entity.DataFldEntity;
import io.dfjinxin.modules.meta.service.DataFldService;


@Service("dataFldService")
public class DataFldServiceImpl extends ServiceImpl<DataFldDao, DataFldEntity> implements DataFldService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DataFldEntity> page = this.page(
                new Query<DataFldEntity>().getPage(params),
                new QueryWrapper<DataFldEntity>()
        );

        return new PageUtils(page);
    }
}