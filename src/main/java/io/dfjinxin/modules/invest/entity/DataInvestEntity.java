package io.dfjinxin.modules.invest.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by GaoPh on 2019/6/18.
 */
@Data
@TableName("data_tbl")
public class DataInvestEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 数据表ID
     */
    @TableId
    private int dataTblid;


    /**
     * 数据表物理名称
     */
    private String dataTblPhysNm;

    /**
     * 数据表中文名
     */
    private String dataTblCnNm;

    /**
     * 数据源ID
     */
    private String dataSrcId;

    /**
     * 数据来源
     */
    private String dataSrcNa;

    /**
     * 数据库
     */
    private String dbid;

    /**
     * 数据表更新日期
     */
    private String updDt;

    /**
     * 元数据状态
     */
    private String metaStatus;

    /**
     * 关键字
     * @return
     */
    private String tableKeys;
    /**
     * 曾全量标识
     */
    private String incrOrFull;

    /**
     * 增全量中文名称
     */
    private String variableNm;

    /**
     * 数据接入周期
     */
    private Integer loadFreq;

    @TableField(exist = false)
    private String loadFreqStr;
    @TableField(exist = false)
    private Integer dbUsageid;

}
