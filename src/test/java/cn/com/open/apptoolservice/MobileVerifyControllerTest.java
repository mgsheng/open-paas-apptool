package cn.com.open.apptoolservice;

import cn.com.open.apptoolservice.util.SignatureUtil;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 个人三大运营商手机号码验证
 */
public class MobileVerifyControllerTest {

    @Test
    public void attribution() {
        String url =  Constant.BASEURL + "/mobile/verify";
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246974");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(responseEntity.getBody());
    }

}
