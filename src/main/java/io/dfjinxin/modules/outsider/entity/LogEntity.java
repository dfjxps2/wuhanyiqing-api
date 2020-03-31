package io.dfjinxin.modules.outsider.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * @author zhujiazhou
 *
 */
@TableName("log")
public class LogEntity {
	
	@TableId(type=IdType.AUTO)
	private Integer id;
	
	private String user;
	   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date time;
	private String type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LogEntity(String user, Date time, String type) {
		this.user = user;
		this.time = time;
		this.type = type;
	}
	public LogEntity() {
	}
}
