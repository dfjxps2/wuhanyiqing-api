package template;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/4/7 15:06
 * @Version: 1.0
 */

public class Test6 {

    @Test
    public void t1() {
        //1.加载配置文件获取Factory对象
//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
//        //2.获取SecurityManager对象
//        SecurityManager securityManager = factory.getInstance();
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //3.将SecurityManager添加到系统
        SecurityUtils.setSecurityManager(securityManager);
        //4.通过SecurityManager获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        //账号密码是客户端提交的数据
        AuthenticationToken token = new UsernamePasswordToken("root", "123456");
        //5.实现认证操作
        try {
            subject.login(token);
            System.out.println("认证成功");
        } catch (UnknownAccountException e) {
            System.out.println("账号输入错误。，，，");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码输入错误。。。");
        }
    }

}
