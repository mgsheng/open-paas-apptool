package cn.com.open.apptoolservice.app.common;

/**
 * Created by guxuyang on 07/07/2017.
 */
public enum ZxptEntityEnum {

	VERSION("zxpt_version","zxptVersion"),
	CHARSET("input_charset","inputCharset"),
	SOURCE("zxpt_source","zxptSource"),
	DES("zxpt_des","zxptDes"),
	APP("zxpt_app","zxptApp"),
	TRANCODE("zxpt_tranCode","zxptTranCode"),
	ENTITYAUTHCODE("zxpt_entityAuthCode","zxptEntityAuthCode"),
	TRANREF("zxpt_tranref","zxptTranref"),
	RESERVE("zxpt_reserve","zxptReserve"),
	CHANNELNO("zxpt_cert_type","channelNo"),
	PUBLICKEY("zxpt_public_key","zxptPublicKey"),
	MD5KEY("zxpt_md5_key","zxptMd5Key"),
	SENDER("zxpt_sender","zxptSender"),
	PRIVATEKEY("zxpt_key","zxptKey")
	
    ;
    String code;
    String message;

    ZxptEntityEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

}
