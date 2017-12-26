package cn.com.open.apptoolservice.app.common;

/**
 * 记录信息
 * Created by guxuyang on 10/07/2017.
 */
public enum ImgRecordInfoStatus {

    success(1, "成功"),
    fail(2, "失败"),
    exception(3, "异常"),
    ;

    Integer code;
    String message;

    ImgRecordInfoStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
