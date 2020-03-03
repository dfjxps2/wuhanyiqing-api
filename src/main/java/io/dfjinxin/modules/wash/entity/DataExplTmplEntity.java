package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据探查模板
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Data
@TableName("data_expl_tmpl")
public class DataExplTmplEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据探查模板ID
	 */
	@TableId
	private Integer dataExplTmplid;
	/**
	 * 数据探查模板名称
	 */
	private String dataExplTmplNm;
	/**
	 * 数据探查模板描述
	 */
	private String dataExplTmplDesc;
	/**
	 * 模板类型
	 */
	private Integer tmplType;
	/**
	 * 租户ID
	 */
	private Integer tnmtid;

}
