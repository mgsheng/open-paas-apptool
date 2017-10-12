package cn.com.open.apptoolservice.app.common;

public enum MobileVerifyType {
    TCL("tcl","tcl"),
    ALIYUN("aliyun", "阿里云");

    String code;
    String message;

    MobileVerifyType() {
    }

    MobileVerifyType(String code, String message) {
        this.code = code;
        this.message = message;
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

    public static MobileVerifyType getByCode(String code) {
        MobileVerifyType[] values = MobileVerifyType.values();
        for (MobileVerifyType value : values) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
