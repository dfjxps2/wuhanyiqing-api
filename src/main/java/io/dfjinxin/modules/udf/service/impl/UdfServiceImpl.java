package io.dfjinxin.modules.udf.service.impl;

import io.dfjinxin.common.exception.RRException;
import io.dfjinxin.common.utils.R;
import io.dfjinxin.common.utils.SSH;
import io.dfjinxin.config.JavaUdfProperties;
import io.dfjinxin.config.SystemParams;
import io.dfjinxin.modules.udf.service.UdfService;
import io.dfjinxin.modules.udf.utils.ReadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class UdfServiceImpl implements UdfService {

    private static Logger logger = LoggerFactory.getLogger(UdfServiceImpl.class);

    @Autowired
    private JavaUdfProperties javaUdfProperties;

    @Autowired
    private SystemParams systemParams;

    /**
     * chClassName：script表达式
     *
     */
    public void readWriteUdf(String chClassName, String chSource, String chFuncName, String type) throws IOException {
        String javaFileStr = ReadFileUtils.readUdfFile(javaUdfProperties.getSource() + type + ".java");
        String jsFileStr = ReadFileUtils.readUdfFile(javaUdfProperties.getSource() + ".js");

        /**
         * 替换java udf文件中的字符串
         * 源文件字符串：sourceFileStr
         *
         * 源文件类名：sClassName
         * 更改后文件类名：chClassName
         *
         * 源文件script文件源码：sSource
         * 更改后文件script源码：chSource
         *
         * 源文件script函数名：sFuncName
         * 更改后文件script函数名chFuncName
         */
        String javaString = ReadFileUtils.replaceUdf(javaFileStr, javaUdfProperties.getName(), chClassName, javaUdfProperties.getScript(), chSource, javaUdfProperties.getFuncname(), chFuncName);
        String jsString = ReadFileUtils.replaceUdf(jsFileStr, javaUdfProperties.getName(), chClassName, javaUdfProperties.getScript(), chSource, javaUdfProperties.getFuncname(), chFuncName);

        ReadFileUtils.writeUdfFile(javaString, new File(javaUdfProperties.getPath() + chClassName + ".java"));
        ReadFileUtils.writeUdfFile(jsString, new File(javaUdfProperties.getPath() + chClassName + ".js"));
    }

    //"register-udf.sh /home/cleanse/udf/java/JSUDF-STR.java /home/cleanse/udf/java/JSUDF.js udf36 com.dfjx.JSUDF udf36"
    @Override
    public int uploadUdf(String className) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("register-udf.sh ");
        sb.append(javaUdfProperties.getPath() + className + ".java ");
        sb.append(javaUdfProperties.getPath() + className + ".js ");
        sb.append(className);
        sb.append(" com.dfjx." + className + " ");
        sb.append(className);
        R res = SSH.execScript(sb.toString(), systemParams);
        if ("0".equals(res.get("code")+ "")) {
        } else {
            throw new RRException(sb.toString() + "==>uploadUdf 执行失败:" + res.get("code")+","+res.get("msg"));
        }
        return 0;
    }

    public void deleteFile(String className) throws IOException {
        ReadFileUtils.delete(javaUdfProperties.getPath() + className + ".java");
        ReadFileUtils.delete(javaUdfProperties.getPath() + className + ".class");
    }

}
