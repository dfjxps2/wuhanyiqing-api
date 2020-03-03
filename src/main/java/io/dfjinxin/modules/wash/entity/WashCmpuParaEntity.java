package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 清洗运算参数
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Data
@TableName("wash_cmpu_para")
public class WashCmpuParaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数ID
	 */
	@TableId
	private Integer paraid;
	/**
	 * 参数值
	 */
	private String paraValue;

}
