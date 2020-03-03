package io.dfjinxin.modules.meta.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据源
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 13:23:10
 */
@Data
@TableName("data_src")
public class DataSrcEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据源ID
	 */
	@TableId
	private Integer dataSrcid;
	/**
	 * 数据源名称
	 */
	private String dataSrcNm;

	private String dataSrcDepCd;//数据源部门代码

	private String dataSrcDepSysCd;//数据源部门业务系统代码

	/**
	 * 部门ID
	 */
	private Integer departmentId;
}
