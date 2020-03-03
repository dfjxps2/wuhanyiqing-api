package io.dfjinxin.modules.test.service.impl;

import io.dfjinxin.common.utils.SpringContextUtils;
import io.dfjinxin.modules.test.service.TestService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

    public String test(String keyValue){
        return keyValue;
    }

    public String toVal(String keyValue, String sql){
        return ((TestService)SpringContextUtils.getBean("testService")).val(keyValue, sql);
//        return this.val(keyValue, sql);
    }

    //缓存key前缀testcache，根据缓存类型设置，标签TAG:，存储STORAGE:等等
    @Cacheable(value="testcache:",key="#keyValue")
    public String val(String keyValue, String sql){
        //执行数据库操作
        System.out.println("=============test=============" + sql);

        return sql;
    }
}
