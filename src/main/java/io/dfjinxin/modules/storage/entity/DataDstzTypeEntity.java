package io.dfjinxin.modules.storage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据脱敏类型
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-18 13:40:58
 */
@Data
@TableName("data_dstz_type")
public class DataDstzTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据脱敏类型ID
	 */
	@TableId
	private Integer dataDstzTypeid;
	/**
	 * 数据脱敏类型名称
	 */
	private String dataDstzTypeNm;

}
