/**
 * 2019 东方金信
 */

package io.dfjinxin.modules.sys.controller;

import io.dfjinxin.common.utils.HttpContextUtils;
import io.dfjinxin.common.utils.IPUtils;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.modules.outsider.entity.LogEntity;
import io.dfjinxin.modules.outsider.service.LogService;
import io.dfjinxin.modules.sys.entity.SysLogEntity;
import io.dfjinxin.modules.sys.entity.SysUserEntity;
import io.dfjinxin.modules.sys.form.SysLoginForm;
import io.dfjinxin.modules.sys.service.*;
import io.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Mark sunlightcs@gmail.com
 */
@Api(tags = "系统登录")
@RestController
public class SysLoginController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;
    @Autowired
    private SysCaptchaService sysCaptchaService;
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private LogService logService;

    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form) throws IOException {
//		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
//		if(!captcha){
//			return R.error("验证码不正确");
//		}

        Long statrTime = System.currentTimeMillis();

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        R r = sysUserTokenService.createToken(user.getUserId());
//        this.saveLoginLog(statrTime, form.getUsername(), "/sys/login");
        LogEntity log = new LogEntity(form.getUsername(), new Date(), "登录");
        logService.saveOrUpdate(log);
        return r;
    }

    public void saveLoginLog(Long statrTime, String username, String bussMethodName) {
        SysLogEntity sysLog = new SysLogEntity();
        sysLog.setOperation("用户登录");
        sysLog.setCreateDate(new Date());
        //获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        //用户名
//        String username = ((SysUserEntity) SecurityUtils.getSubject().getPrincipal()).getUsername();
        sysLog.setUsername(username);
        sysLog.setTime(System.currentTimeMillis() - statrTime);
        sysLog.setCreateDate(new Date());
        String methodName = bussMethodName;
        sysLog.setMethod(bussMethodName + "()");
        //保存系统日志
        sysLogService.save(sysLog);
    }


    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public R logout() {
        sysUserTokenService.logout(getUserId());
        return R.ok();
    }

}
