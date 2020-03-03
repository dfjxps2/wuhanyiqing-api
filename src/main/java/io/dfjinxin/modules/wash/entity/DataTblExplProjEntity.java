package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据表探查项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@Data
@TableName("data_tbl_expl_proj")
public class DataTblExplProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据表ID
	 */
	@TableId
	private Integer dataTblid;
	/**
	 * 数据探查模板ID
	 */
	private Integer dataExplTmplid;
	/**
	 * 项目类型
	 */
	private Integer projType;

}
