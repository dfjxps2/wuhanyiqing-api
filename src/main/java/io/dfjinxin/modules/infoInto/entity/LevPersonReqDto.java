package io.dfjinxin.modules.infoInto.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Desc:
 * @Author: z.h.c
 * @Date: 2020/4/2 10:57
 * @Version: 1.0
 */

@Data
@Accessors(chain = true)
public class LevPersonReqDto {

    //用户名不能为空")
    private String name;

    //"手机号不能为空")
    private String phone;

    //证件类型不能为空")
    private String cardType;

//    @NotBlank(message = "证件号不能为空")
//    private String cardNum;

    //"离开时间不能为空")
    private String levTime;

    /*@NotBlank(message = "返回省市不能为空")
    private String levCity;*/

    //"返程方式不能为空")
    private String levBy;

    private String hj;//户籍

    private String levLiveType;//滞留居住方式

    private String levLiveAddress;//滞留居住地址；

    private String backProvince;//返回省
    private String backCity;//返回市
    private String zoneCd;
    private Integer pageSize;
    private Integer pageIndex;
}
