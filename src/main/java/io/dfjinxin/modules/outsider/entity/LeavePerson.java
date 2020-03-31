package io.dfjinxin.modules.outsider.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 离开武汉的人
 * @author zhujiazhou
 */
@TableName("lev_person")
public class LeavePerson {

	@TableId(type=IdType.AUTO)
	private Integer id;//ID
	private String name;//姓名
	private String phone;//手机号
	private String cardType;//证件类型
	private String cardNum;//证据号码
	private String levTime; //离汉时间
	private String levCity;// '返回省市',
	private String levBy;//返程方式
	private String zoneCd;//区id
	   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;//创建时间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getLevTime() {
		return levTime;
	}
	public void setLevTime(String levTime) {
		this.levTime = levTime;
	}
	public String getLevCity() {
		return levCity;
	}
	public void setLevCity(String levCity) {
		this.levCity = levCity;
	}
	public String getLevBy() {
		return levBy;
	}
	public void setLevBy(String levBy) {
		this.levBy = levBy;
	}
	public String getZoneCd() {
		return zoneCd;
	}
	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
