//package io.dfjinxin.modules.infoInto.service.impl;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import io.dfjinxin.common.utils.PageUtils;
//import io.dfjinxin.common.utils.Query;
//
//import io.dfjinxin.modules.infoInto.dao.SysOrgDao;
//import io.dfjinxin.modules.infoInto.entity.SysOrgEntity;
//import io.dfjinxin.modules.infoInto.service.SysOrgService;
//
//
//@Service("sysOrgService2")
//public class SysOrgServiceImpl extends ServiceImpl<SysOrgDao, SysOrgEntity> implements SysOrgService {
//
//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<SysOrgEntity> page = this.page(
//                new Query<SysOrgEntity>().getPage(params),
//                new QueryWrapper<SysOrgEntity>()
//        );
//
//        return new PageUtils(page);
//    }
//
//    @Override
//    public List<SysOrgEntity> queryOrg() {
//        QueryWrapper where = new QueryWrapper();
//        where.ne("parent_id", 0);
//        where.orderByAsc("org_id");
//        return baseMapper.selectList(where);
//    }
//
//}
