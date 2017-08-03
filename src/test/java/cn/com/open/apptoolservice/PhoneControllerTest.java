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
 * 手机归属地
 * Created by guxuyang on 12/07/2017.
 */
public class PhoneControllerTest {

    private static final String BASEURL = "http://localhost:8080";
    //private static final String BASEURL = "";
    private static final String APPKEY = "wkdsjdhshoewihoi2ioio34i";
    private static final String APPSECRET = "2kwi3k9skskdlf2sssss";

    @Test
    public void attribution() {
        String url =  BASEURL + "/phone/attribution";
        String number = "13699246974";
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = SignatureUtil.getHeaders(APPKEY, APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.POST, httpEntity, String.class);
        System.out.println(responseEntity.getBody());
    }

}
