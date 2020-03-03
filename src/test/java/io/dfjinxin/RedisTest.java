//package io.dfjinxin;
//
//import com.alibaba.fastjson.JSON;
//import io.dfjinxin.common.utils.RedisUtils;
//import io.dfjinxin.modules.sys.entity.SysUserEntity;
//import org.apache.commons.lang.builder.ToStringBuilder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.BoundListOperations;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class RedisTest {
//	@Autowired
//	private RedisUtils redisUtils;
//	@Autowired
//	private RedisTemplate redisTemplate;
//
//	/**
//	 * 插入
//	 * redisTemplate.opsForList().rightPushAll("key", list);
//	 */
//
//	@Test
//	public void insert(){
//		List<SysUserEntity> lists = new ArrayList<>();
//		for(int i=0; i<2; i++){
//			SysUserEntity e = new SysUserEntity();
//			e.setUsername("name_" + i);
//			e.setEmail("email_" + i);
////			lists.add(JSON.toJSONString(e));
//			lists.add(e);
//		}
//		redisTemplate.opsForList().rightPushAll("testkey", lists);
//	}
//
//	@Test
//	public void query(){
////        BoundListOperations listOps = redisTemplate.boundListOps("testkey");
////        long size = listOps.size();
////        List<SysUserEntity> lists = listOps.range(0, 10);
//
//		List<SysUserEntity> lists = redisTemplate.opsForList().range("testkey", 0, 10);
//		System.out.println(lists);
////		lists.forEach(v -> {
////			System.out.println(JSON.parseObject(v, SysUserEntity.class));
////		});
//	}
//
//	/**
//	 * 查询
//	 * opsForList().range(key, start, end);  取范围值  redis里面的list下标从0开始
//	 * redisTemplate.opsForList().range(key, start, end)
//	 */
//
//	@Test
//	public void contextLoads() {
//		SysUserEntity user = new SysUserEntity();
//		user.setEmail("qqq@qq.com");
//		redisUtils.set("user", user);
//
//		System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity.class)));
//	}
//
//}
