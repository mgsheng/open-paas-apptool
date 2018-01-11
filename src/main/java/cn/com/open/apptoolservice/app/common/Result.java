package cn.com.open.apptoolservice.app.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class Result {

    public static final Integer SUCCESS = 1;
    public static final Integer ERROR = 0;

    private Integer status;
    private String message;
    private String errorCode;
    private Object payload;
    @JSONField(serialize = false)
    private Map<String, Object> ignore;

    public Result() {
    }

    public Result(Integer status, String message, String errorCode, Object payload) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.payload = payload;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Map<String, Object> getIgnore() {
        return ignore;
    }

    public void setIgnore(Map<String, Object> ignore) {
        this.ignore = ignore;
    }
}