package cn.com.open.apptoolservice.app.common;

/**
 * Created by guxuyang on 07/07/2017.
 */
public enum FormEnum {

	APPKEY("AppKey","APPKEY"),
    ;
    String code;
    String message;

    FormEnum() {
    }

    FormEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static FormEnum getByCode(String code) {
        FormEnum[] values = FormEnum.values();
        for (FormEnum value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
