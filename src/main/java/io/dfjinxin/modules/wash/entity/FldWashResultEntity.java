package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 字段清洗结果
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:37:27
 */
@Data
@TableName("fld_wash_result")
public class FldWashResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 作业ID
	 */
	@TableId
	private Integer jobid;
	/**
	 * 字段ID
	 */
	private Integer fldid;
	/**
	 * 数据清洗运算ID
	 */
	private Integer dataWashCmpuid;
	/**
	 * 检查项目ID
	 */
	private Integer chkProjid;
	/**
	 * 清洗记录数
	 */
	private Integer washRecQty;

}
