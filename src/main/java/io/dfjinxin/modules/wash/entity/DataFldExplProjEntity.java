package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据字段探查项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Data
@TableName("data_fld_expl_proj")
public class DataFldExplProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段ID
	 */
	@TableId
	private Integer fldid;
	/**
	 * 数据探查模板ID
	 */
	private Integer dataExplTmplid;
	/**
	 * 项目类型
	 */
	private Integer projType;

}