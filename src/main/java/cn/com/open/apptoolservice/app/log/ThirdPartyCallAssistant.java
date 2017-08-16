package cn.com.open.apptoolservice.app.log;


import cn.com.open.apptoolservice.app.common.LogTypeEnum;
import cn.com.open.apptoolservice.app.common.ServiceProviderEnum;
import cn.com.open.apptoolservice.app.log.support.AliyunResponseBean;
import cn.com.open.apptoolservice.app.log.support.ThirdPartyCallLog;
import cn.com.open.apptoolservice.app.utils.DateUtil;
import cn.com.open.apptoolservice.app.zxpt.http.HttpClientUtil;
import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ThirdPartyCallAssistant {

    private final static Logger log = LoggerFactory.getLogger(ThirdPartyCallAssistant.class);

    @Autowired
    private ApptoolServiceLogSender apptoolServiceLogSender;
    @Value("${apptool.thirdparty.log.onOff}")
    private String apptoolThirdpartyLogOnOff;

    public AliyunResponseBean attribution(String url, Map<String, String> headers, String httpMethod, Map<String, String> querys, String channelValue) throws Exception {
        AliyunResponseBean aliyunResponseBean = null;
        long startTime = System.currentTimeMillis(); //请求开始时间
        try {
            HttpResponse response = HttpUtils.doGet(url, null, httpMethod, headers, querys);
            aliyunResponseBean = new AliyunResponseBean();
            Map<String, String> responseHeaders = new HashMap<>();
            responseHeaders.put("X-Ca-Request-Id", Arrays.toString(response.getHeaders("X-Ca-Request-Id")));
            responseHeaders.put("X-Ca-Error-Message", Arrays.toString(response.getHeaders("X-Ca-Error-Message")));
            aliyunResponseBean.setHeards(responseHeaders);
            String text = EntityUtils.toString(response.getEntity());
            log.info(String.format("aliyun response text { %s } ", text));
            aliyunResponseBean.setJson(text);
            return aliyunResponseBean;
        } finally {
            if ("on".equals(apptoolThirdpartyLogOnOff)) {
                long endTime = System.currentTimeMillis();
                ThirdPartyCallLog thirdPartyCallLog = new ThirdPartyCallLog();
                thirdPartyCallLog.setChannelValue(channelValue);
                thirdPartyCallLog.setChannelName(ServiceProviderEnum.getNameByValue(channelValue));
                thirdPartyCallLog.setExecutionTime(endTime - startTime);
                thirdPartyCallLog.setLogType(LogTypeEnum.ThIRDPARTY.getCode());
                thirdPartyCallLog.setCreateTime(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                if (aliyunResponseBean != null) {
                    thirdPartyCallLog.setResponseText(aliyunResponseBean.getJson());
                    thirdPartyCallLog.setResponseHeaderParam(JSONObject.valueToString(aliyunResponseBean.getHeards()));
                }
                apptoolServiceLogSender.sendThirdPartyCallLog(thirdPartyCallLog);
            }
        }
    }

    public void zxMobileVerifyLog(long startTime, String decodeXml, String channelValue) {
        try {
            if ("on".equals(apptoolThirdpartyLogOnOff)) {
                long endTime = System.currentTimeMillis();
                ThirdPartyCallLog thirdPartyCallLog = new ThirdPartyCallLog();
                thirdPartyCallLog.setChannelValue(channelValue);
                thirdPartyCallLog.setChannelName(ServiceProviderEnum.getNameByValue(channelValue));
                thirdPartyCallLog.setExecutionTime(endTime - startTime);
                thirdPartyCallLog.setLogType(LogTypeEnum.ThIRDPARTY.getCode());
                thirdPartyCallLog.setCreateTime(DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                thirdPartyCallLog.setResponseText(decodeXml);
                apptoolServiceLogSender.sendThirdPartyCallLog(thirdPartyCallLog);
            }
        } catch (Exception e) {
            log.error("记录个人三大运营商手机号码验证日志错误", e);
        }

    }
}
