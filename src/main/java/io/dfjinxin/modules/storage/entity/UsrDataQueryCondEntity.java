package io.dfjinxin.modules.storage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户数据查询条件
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:19:07
 */
@Data
@TableName("usr_data_query_cond")
public class UsrDataQueryCondEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户数据查询条件ID
	 */
	@TableId
	private Integer usrDataQueryCondid;
	/**
	 * 用户查询ID
	 */
	private Integer usrQueryid;
	/**
	 * 字段ID
	 */
	private Integer fldid;
	/**
	 * 操作符
	 */
	private String oprSmb;
	/**
	 * 操作数
	 */
	private String operQty;
	/**
	 * 查询条件次序
	 */
	private Integer queryCondOrd;

}
