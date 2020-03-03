package io.dfjinxin.modules.storage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户数据查询
 * 
 * @author zdl
 * @email 492587402@qq.com
 * @date 2019-06-15 19:17:04
 */
@Data
@TableName("usr_data_query")
public class UsrDataQueryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户查询ID
	 */
	@TableId
	private Integer usrQueryid;
	/**
	 * 用户登录ID
	 */
	private String usrLognid;
	/**
	 * 数据表ID
	 */
	private Integer dataTblid;
	/**
	 * 用户数据查询名称
	 */
	private String usrDataQueryNm;

}
