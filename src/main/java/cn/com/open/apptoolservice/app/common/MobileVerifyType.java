package cn.com.open.apptoolservice.app.common;

public enum MobileVerifyType {
    TCL("tcl","tcl"),
    ALIYUN("aliyun", "阿里云");

    String code;
    String message;

    MobileVerifyType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
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
