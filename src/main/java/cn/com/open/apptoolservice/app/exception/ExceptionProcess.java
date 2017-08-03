package cn.com.open.apptoolservice.app.exception;

import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.utils.HtmlUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理
 *
 * Created by guxuyang on 04/07/2017.
 */
@ControllerAdvice
public class ExceptionProcess {

    private final Logger log = LoggerFactory.getLogger(ExceptionProcess.class);

    @ExceptionHandler(Throwable.class)
    public void processException(HttpServletResponse response, Throwable ex) {
        log.error(ex.getMessage(), ex);
        Result result = new Result(Result.ERROR, ExceptionEnum.SysException.getMessage(), ExceptionEnum.SysException.getCode(), null);
        String resultJson = JSONObject.toJSONString(result);
        HtmlUtil.writeJson(response, resultJson);
    }

}
