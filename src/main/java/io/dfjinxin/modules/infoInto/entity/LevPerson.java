package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/3/31 17:22
 * @Version: 1.0
 */
@Data
@TableName("lev_person")
public class LevPerson {
    @TableId
    private Long id;

    private String name;
    private String phone;
    private String cardType;
    private String cardNum;
    private String levTime;
    private String levCity;
    private String levBy;
    private String zoneCd;
    private Date createTime;
}
