package cn.com.open.apptoolservice.app.common;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	protected static boolean nullAndEmpty(String str){
        return null==str||str.isEmpty()||"".equals(str.trim());
    }
	 protected boolean nullEmptyBlankJudge(String str){
        return null==str||str.isEmpty()||"".equals(str.trim());
    }
    protected boolean paraMandatoryCheck(List<String> params){
        for(String param:params){
            if(nullEmptyBlankJudge(param)){
                return false;
            }
        }
        return true;
    }
    
    protected boolean paramCheck(List<Integer> params){
        for(Integer param:params){
            if(param==-1){
                return false;
            }
        }
        return true;
    }

    protected void responseErrorJason(HttpServletResponse response, String errorCode, String message) {
        responseErrorJason(response, errorCode, message, null);
    }

    protected void responseErrorJason(HttpServletResponse response, String errorCode, String message, Object payload) {
        Result result = new Result();
        result.setStatus(Result.ERROR);
        result.setErrorCode(errorCode);
        result.setMessage(message);
        if (payload != null) {
            result.setPayload(payload);
        }
        responseJason(response, result);
    }

    protected void responseErrorJason(HttpServletResponse response, String message) {
        responseErrorJason(response, "0", message);
    }

    protected void responseErrorJason(HttpServletResponse response, ExceptionEnum exceptionEnum) {
        responseErrorJason(response, exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    protected void responseErrorJason(HttpServletResponse response, ExceptionEnum exceptionEnum, Object payload) {
        responseErrorJason(response, exceptionEnum.getCode(), exceptionEnum.getMessage(), payload);
    }

    protected void responseErrorCodeJason(HttpServletResponse response, String errorCode) {
        ExceptionEnum apiErrorCode = ExceptionEnum.valueOf(errorCode);
        if (apiErrorCode == null) {
            return;
        }
        responseErrorJason(response, apiErrorCode);
    }

    protected void responseSuccessJason(HttpServletResponse response, Object payload) {
        responseSuccessJason(response, payload, "");
    }

    protected void responseSuccessJason(HttpServletResponse response, Object payload, String message) {
        Result result = new Result();
        result.setStatus(Result.SUCCESS);
        result.setMessage(message);
        if (payload != null) {
            result.setPayload(payload);
        }
        responseJason(response, result);
    }

    protected void responseJason(HttpServletResponse response, Result result) {
        String resultJson = JSONObject.toJSONString(result);
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(resultJson);
            log.info(resultJson);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
