package cn.com.open.apptoolservice.app.record;

import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RecordRemoteCallAspect {

    Logger log = LoggerFactory.getLogger(RecordRemoteCallAspect.class);

    @Autowired
    private ApptoolRecordInfoService apptoolRecordInfoService;

    @Pointcut("@annotation(cn.com.open.apptoolservice.app.record.RecordRemoteCall)")
    public void controllerAspect() {
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "returnValue")
    public void afterMethod(Object returnValue) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            Result result = (Result) returnValue;
            apptoolRecordInfoService.saveImageRecordInfo(request, result);
            log.info("hshshsh");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}