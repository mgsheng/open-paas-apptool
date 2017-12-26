package cn.com.open.apptoolservice.app.common;

/**
 * 描述：
 * author：mason(ma)
 * 日期：16/3/11.
 */
public enum MobileResultEnum {
    RESULTSUCCESS("1","一致"),
    RESULTERROR("2", "不一致"),
    RESULTNOVERIFY("0","无法验证")
    ;
    String code;
    String message;

    MobileResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
