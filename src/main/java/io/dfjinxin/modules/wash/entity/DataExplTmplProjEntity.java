package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据探查模板项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:47:39
 */
@Data
@TableName("data_expl_tmpl_proj")
public class DataExplTmplProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据探查模板ID
	 */
	//@TableId(type= IdType.NONE)
	private Integer dataExplTmplid;
	/**
	 * 检查项目ID
	 */
	private Integer chkProjid;
	/**
	 * 执行顺序
	 */
	private Integer exctOrd;

}
