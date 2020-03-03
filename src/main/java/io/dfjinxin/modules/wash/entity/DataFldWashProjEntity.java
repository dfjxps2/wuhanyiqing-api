package io.dfjinxin.modules.wash.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 数据字段清洗项目
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-06-14 16:43:58
 */
@Data
@TableName("data_fld_wash_proj")
public class DataFldWashProjEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 字段ID
	 */
	//@TableId(type= IdType.NONE)
	private Integer fldid;
	/**
	 * 数据清洗运算ID
	 */
	private Integer dataWashCmpuid;
	/**
	 * 执行顺序
	 */
	private Integer exctOrd;
	/**
	 * 参数ID
	 */
	private Integer paraid;

}
