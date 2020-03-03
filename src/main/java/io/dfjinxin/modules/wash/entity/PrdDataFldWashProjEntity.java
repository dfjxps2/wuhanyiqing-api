package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 已发布数据字段清洗项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-29 10:27:33
 */
@Data
@TableName("prd_data_fld_wash_proj")
public class PrdDataFldWashProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段ID
	 */
	@TableId(type= IdType.INPUT)
	private Integer fldid;
	/**
	 * 数据清洗运算ID
	 */

	private Integer dataWashCmpuid;
	/**
	 * 清洗规则发布时间
	 */
	private Date releaseTime;
	/**
	 * 执行顺序
	 */
	private Integer exctOrd;
	/**
	 * 参数ID
	 */
	private Integer paraid;

}
