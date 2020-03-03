package io.dfjinxin.modules.udf.service;

import java.io.IOException;

public interface UdfService {

    void readWriteUdf(String chClassName, String chSource, String chFuncName, String type) throws IOException;

    /**
     *
     * @param className 被打包class文件的名称
     */
    int uploadUdf(String className);


    void deleteFile(String className) throws IOException;


}
