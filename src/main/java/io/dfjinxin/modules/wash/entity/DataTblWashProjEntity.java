package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据表清洗项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Data
@TableName("data_tbl_wash_proj")
public class DataTblWashProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据表ID
	 */
	@TableId
	private Integer dataTblid;
	/**
	 * 数据清洗运算ID
	 */
	private Integer dataWashCmpuid;
	/**
	 * 检查项目ID
	 */
	private Integer chkProjid;
	/**
	 * 执行顺序
	 */
	private Integer exctOrd;
	/**
	 * 参数ID
	 */
	private Integer paraid;

}
