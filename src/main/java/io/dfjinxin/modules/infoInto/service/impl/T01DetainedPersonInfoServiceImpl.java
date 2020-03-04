package io.dfjinxin.modules.infoInto.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dfjinxin.common.utils.PageUtils;
import io.dfjinxin.modules.infoInto.dao.T01DetainedPersonInfoDao;
import io.dfjinxin.modules.infoInto.entity.T01DetainedPersonInfoEntity;
import io.dfjinxin.modules.infoInto.service.T01DetainedPersonInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("t01DetainedPersonInfoService")
public class T01DetainedPersonInfoServiceImpl extends ServiceImpl<T01DetainedPersonInfoDao, T01DetainedPersonInfoEntity> implements T01DetainedPersonInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<T01DetainedPersonInfoEntity> page = this.page(
//                new Query<T01DetainedPersonInfoEntity>().getPage(params),
//                new QueryWrapper<T01DetainedPersonInfoEntity>()
//        );
//
//        return new PageUtils(page);

        Page page = new Page((Integer) params.get("pageIndex"), (Integer) params.get("pageSize"));
        page = (Page) super.baseMapper.queryPage(page, params);
        return new PageUtils(page);
    }

    /**
     * 根据id查询详情
     *
     * @param id
     * @return
     */
    @Override
    public T01DetainedPersonInfoEntity queryById(String id) {
        return baseMapper.queryDetailById(new Long(id));
    }
}
