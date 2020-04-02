package io.dfjinxin.modules.infoInto.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    @TableId(type = IdType.AUTO)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotBlank(message = "手机号不能为空")
    @Pattern(message ="手机号格式错误" ,regexp = "^1(3|4|5|7|8)\\d{9}$")
    private String phone;

    @NotBlank(message = "证件类型不能为空")
    private String cardType;

    @NotBlank(message = "证件号不能为空")
    private String cardNum;

    @NotBlank(message = "离开时间不能为空")
    private String levTime;

    /*@NotBlank(message = "返回省市不能为空")
    private String levCity;*/

    @NotBlank(message = "返程方式不能为空")
    private String levBy;

    private String hj;//户籍

    @NotBlank(message = "滞留居住方式不能为空")
    private String levLiveType;//滞留居住方式

    @NotBlank(message = "滞留居住地址不能为空")
    private String levLiveAddress;//滞留居住地址；

    @NotBlank(message = "返回省不能为空")
    private String backProvince;//返回省

    @NotBlank(message = "返回市不能为空")
    private String backCity;//返回市

    private String zoneCd;
    private Date createTime;
}
