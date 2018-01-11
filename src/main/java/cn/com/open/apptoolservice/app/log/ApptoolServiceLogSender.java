package cn.com.open.apptoolservice.app.log;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class ApptoolServiceLogSender {

    private static final Logger log= LoggerFactory.getLogger(ApptoolServiceLogSender.class);
    private static final String LOG_DB_NAME = "apptoolservocelog";

    @Autowired
    private RestTemplate restTemplate;
    @Value("${base.service.log.url}")
    private String baseServiceLogUrl;

    /**
     * 记录接口调用日志
     */
    void sendServiceLog(JSONObject jsonObject) {
        MultiValueMap<String, Object> logMap = new LinkedMultiValueMap<>();
        logMap.add("tag", LOG_DB_NAME);
        logMap.add("logData", jsonObject.toJSONString());
        new Thread(() -> sendLog(logMap)).start();
    }

    void sendThirdPartyCallLog(JSONObject thirdPartyCallLog) {
        MultiValueMap<String, Object> logMap = new LinkedMultiValueMap<>();
        logMap.add("tag", LOG_DB_NAME);
        logMap.add("logData", thirdPartyCallLog.toJSONString());
        new Thread(() -> sendLog(logMap)).start();
    }

    private void sendLog(MultiValueMap<String, Object> logMap) {
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(logMap, headers);
            ResponseEntity<String> result = restTemplate.exchange(baseServiceLogUrl, HttpMethod.POST, entity, String.class);
            log.info(result.getBody());
        } catch (Exception e) {
            log.error("调用基础服务日志接口异常", e);
        }
    }

}
