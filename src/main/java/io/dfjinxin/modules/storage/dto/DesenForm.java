package io.dfjinxin.modules.storage.dto;

import io.dfjinxin.modules.storage.utils.StorageUtils;
import lombok.Data;

@Data
public class DesenForm {
    private Integer partid;//数据分区
    private Integer dbId;//数据库id
    private String[] fieldMarksStr;//字段标签
    private Integer[] fieldMarks;
    private String[] tableMarksStr;//表标签
    private Integer[] tableMarks;
    private String fieldName;//字段名称
    private String tableName;//表名称
    private String isDesen;//是否脱敏
    private int labelId;//标签id
    private Integer[] fields;//字段id集合
    private int dataSrcid;//数据源id
    private Integer[] tblIds;//数据表id
    private String dbUsageCd;//用途库代码
    private int pageIndex;//当前页
    private int pageSize;//每页显示条数
    private int start;

    public void markToInt() {
        this.start = (pageIndex - 1) * pageSize;
        this.fieldMarks = StorageUtils.strToInteger(fieldMarksStr);
        this.tableMarks = StorageUtils.strToInteger(tableMarksStr);
    }
}
