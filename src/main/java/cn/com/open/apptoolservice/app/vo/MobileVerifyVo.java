package cn.com.open.apptoolservice.app.vo;
/**
 * 机号码实名验证参数
 * @author admin
 *
 */
public class MobileVerifyVo {
	private String number;
	private String idCard;
	private String realName;
	private String sourceUid;
	private String sourceUserName;
	private String merchantId;
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSourceUid() {
		return sourceUid;
	}
	public void setSourceUid(String sourceUid) {
		this.sourceUid = sourceUid;
	}
	public String getSourceUserName() {
		return sourceUserName;
	}
	public void setSourceUserName(String sourceUserName) {
		this.sourceUserName = sourceUserName;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	

}
