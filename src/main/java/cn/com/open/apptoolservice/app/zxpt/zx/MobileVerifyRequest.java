/****************************************************************************  
 * Copyright © 2016 TCL Technology . All rights reserved.
 * @Title: AuthRequst.java 
 * @Prject: cr-model
 * @Package: com.tcl.cr.auth.third.packet.request 
 * @Description: TODO
 * @author: heqingqing   
 * @date: 2016年10月31日 上午11:25:37 
 * @version: V1.0   
 * ****************************************************************************
 */
package cn.com.open.apptoolservice.app.zxpt.zx;

import java.io.Serializable;




/** 
 * @ClassName: MobileVerifyRequest 
 * @Description: TODO
 */
public class MobileVerifyRequest  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号(请求流水号)
	 */
	private String orderId;
		
	/**
	 * 证件号  目前只支持身份证号码
	 */
	private String certNo;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 渠道编号（1：维氏盾、2：证通、3：鹏元、空：根据路由规则配置接口）
	 */
	private String channelNo;
	
	/**
	 * 信息主体授权码
	 */
	private String entityAuthCode;
	
	/**
	 * 授权时间
	 */
	private String entityAuthDate;
	
	/**
	 * 保留字段
	 */
	private String resv;
    
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getEntityAuthCode() {
		return entityAuthCode;
	}

	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}

	public String getEntityAuthDate() {
		return entityAuthDate;
	}

	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}

	public String getResv() {
		return resv;
	}

	public void setResv(String resv) {
		this.resv = resv;
	}

	@Override
	public String toString() {
		return "MobileVerifyRequest [orderId=" + orderId + ", certNo=" + certNo + ", name=" + name + ", mobile=" + mobile + ", channelNo=" + channelNo
				+ ", entityAuthCode=" + entityAuthCode + ", entityAuthDate=" + entityAuthDate + ", resv=" + resv + "]";
	}

	/* (non Javadoc) 
	 * @Title: toString
	 * @Description: TODO
	 * @return 
	 * @see java.lang.Object#toString() 
	 */
	
}
