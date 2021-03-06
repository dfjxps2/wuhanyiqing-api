package io.dfjinxin.modules.outsider.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 滞汉外地人明细
 * @author zhujiazhou
 *
 */
@TableName("t01_detained_person_info")
public class DetainedPersonInfoEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TableId(type=IdType.AUTO)
	private Integer id;//序号,
	private String address;//当地居住地址
	private String appealChannelCd;//@诉求渠道代码
	//private String appealTypeCd;//诉求类型代码  第二版需求删除字段
	private String areaCd;//行政区划代码
	private String bz;//备注
	private String cardNumber;//身份证号
	private String detainedName;//姓名
	private String destCity;//目的城市
	//private String detainedInfo;//滞留详情  第二版需求删除字段
	private String detainedPersonStatusCd;//@滞留人员状态代码
	private String detainedPersonTypeCd;//滞留人员类型代码
	private String keepStatusCd;//@记录状态代码
	private String resetMode;//安置方式
	private String reviewUserId;//审核用户编号
	//备用yyyy-MM-dd HH:mm:ss
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date submitDate;//填报日期
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date commitDate;//提交日期
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date reviewDate;//审核日期
	
	private String submitUserId;//填报用户编号
	private String telephone;//电话
	
	///////////////////////////////////////////////////////////////////////////第二版需求新增字段/////////////////////////////////////////////////////////////////////////////
	   private  String orderName;//负责人,
	   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	   private  Date salveDateEnd;//救助结束日期,
	   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	  private   Date salveDateStat;//救助开始日期,
	  private   Double salveAmount;//救助金额,
	  private   String placeAreaCd;//户籍行政区划代码,
	  
	  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	  private   Date uptDate;//修改日期,
	  private   String isUser;//@是否有效,
	  private String detainedPersonStatusAi;//滞留人员类型ai,
	  private  String resetModeAi;//安置方式ai,
///////////////////////////////////////////////////////////////////////////第二版需求新增字段/////////////////////////////////////////////////////////////////////////////

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppealChannelCd() {
		return appealChannelCd;
	}
	public void setAppealChannelCd(String appealChannelCd) {
		this.appealChannelCd = appealChannelCd;
	}
	/*public String getAppealTypeCd() {
		return appealTypeCd;
	}
	public void setAppealTypeCd(String appealTypeCd) {
		this.appealTypeCd = appealTypeCd;
	}*/
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getDestCity() {
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
/*	public String getDetainedInfo() {
		return detainedInfo;
	}
	public void setDetainedInfo(String detainedInfo) {
		this.detainedInfo = detainedInfo;
	}*/
	public String getDetainedPersonStatusCd() {
		return detainedPersonStatusCd;
	}
	public void setDetainedPersonStatusCd(String detainedPersonStatusCd) {
		this.detainedPersonStatusCd = detainedPersonStatusCd;
	}
	public String getDetainedPersonTypeCd() {
		return detainedPersonTypeCd;
	}
	public void setDetainedPersonTypeCd(String detainedPersonTypeCd) {
		this.detainedPersonTypeCd = detainedPersonTypeCd;
	}
	public String getKeepStatusCd() {
		return keepStatusCd;
	}
	public void setKeepStatusCd(String keepStatusCd) {
		this.keepStatusCd = keepStatusCd;
	}
	public String getResetMode() {
		return resetMode;
	}
	public void setResetMode(String resetMode) {
		this.resetMode = resetMode;
	}
	public String getReviewUserId() {
		return reviewUserId;
	}
	public void setReviewUserId(String reviewUserId) {
		this.reviewUserId = reviewUserId;
	}
	
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Date getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(Date commitDate) {
		this.commitDate = commitDate;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getSubmitUserId() {
		return submitUserId;
	}
	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDetainedName() {
		return detainedName;
	}
	public void setDetainedName(String detainedName) {
		this.detainedName = detainedName;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderUame) {
		this.orderName = orderUame;
	}
	public Date getSalveDateEnd() {
		return salveDateEnd;
	}
	public void setSalveDateEnd(Date salveDateEnd) {
		this.salveDateEnd = salveDateEnd;
	}
	public Date getSalveDateStat() {
		return salveDateStat;
	}
	public void setSalveDateStat(Date salveDateStat) {
		this.salveDateStat = salveDateStat;
	}
	public Double getSalveAmount() {
		return salveAmount;
	}
	public void setSalveAmount(Double salveAmount) {
		this.salveAmount = salveAmount;
	}
	public String getPlaceAreaCd() {
		return placeAreaCd;
	}
	public void setPlaceAreaCd(String placeAreaCd) {
		this.placeAreaCd = placeAreaCd;
	}
	public Date getUptDate() {
		return uptDate;
	}
	public void setUptDate(Date uptDate) {
		this.uptDate = uptDate;
	}
	public String getIsUser() {
		return isUser;
	}
	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}
	public String getDetainedPersonStatusAi() {
		return detainedPersonStatusAi;
	}
	public void setDetainedPersonStatusAi(String detainedPersonStatusAi) {
		this.detainedPersonStatusAi = detainedPersonStatusAi;
	}
	public String getResetModeAi() {
		return resetModeAi;
	}
	public void setResetModeAi(String resetModeAi) {
		this.resetModeAi = resetModeAi;
	}
	
}
