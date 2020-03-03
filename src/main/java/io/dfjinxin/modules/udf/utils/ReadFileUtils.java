package io.dfjinxin.modules.udf.utils;

import java.io.*;

public class ReadFileUtils {

    /**
     * 读取java udf文件
     */
    public static String readUdfFile(String filePath) throws IOException {
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader bf= new BufferedReader(new FileReader(filePath))) {
            String s = null;
            while ((s = bf.readLine()) != null) {//使用readLine方法，一次读一行
                buffer.append(s + "\n");
            }
        }
        return buffer.toString();
    }

    /**
     * 删除文件
     */
    public static void delete(String filePath) throws IOException {

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 重写java udf文件
     */
    public static void writeUdfFile(String cont, File dist) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(dist))){
            writer.write(cont);
        }
    }

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
    public static String replaceUdf(String sourceFileStr, String sClassName, String chClassName, String sSource, String chSource, String sFuncName, String chFuncName){
        sourceFileStr = sourceFileStr.replace(sClassName, chClassName).replace(sSource, chSource).replace(sFuncName, chFuncName);
        return sourceFileStr;
    }
}
