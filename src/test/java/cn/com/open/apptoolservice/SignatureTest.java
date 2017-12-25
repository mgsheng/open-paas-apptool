package cn.com.open.apptoolservice;


import cn.com.open.apptoolservice.app.Application;
import cn.com.open.apptoolservice.app.utils.DateUtil;
import cn.com.open.apptoolservice.app.utils.MD5;
import cn.com.open.apptoolservice.util.RandomStringUtil;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
public class SignatureTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    
    /**
     * 签名存在空值
     */
    @Test
    public void signature_error1() {
        String number = "13699246974";
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        HttpHeaders headers = new HttpHeaders();
        String timestamp = DateUtil.dateToString(new Date(), "YYYY-MM-dd'T'hh:mm:ss'Z'");
        String signatureNonce = RandomStringUtil.getRandomString(18);
        String platform = "1";
        headers.add("appKey", Constant.APPKEY);
        headers.add("timestamp", timestamp);
        headers.add("signatureNonce", signatureNonce);
        headers.add("platform", null);
        headers.add("signature", MD5.md5(String.format("timestamp=%s&signatureNonce=%s&platform=%s&appSecret=%s", timestamp, signatureNonce, platform, Constant.APPSECRET)));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    /**
     * 测试平台参数错误
     */
    @Test
    public void signature_error2() {
        String number = "13699246974";
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        HttpHeaders headers = new HttpHeaders();
        String timestamp = DateUtil.dateToString(new Date(), "YYYY-MM-dd'T'hh:mm:ss'Z'");
        String signatureNonce = RandomStringUtil.getRandomString(18);
        String platform = "a";
        headers.add("appKey", Constant.APPKEY);
        headers.add("timestamp", timestamp);
        headers.add("signatureNonce", signatureNonce);
        headers.add("platform", platform);
        headers.add("signature", MD5.md5(String.format("timestamp=%s&signatureNonce=%s&platform=%s&appSecret=%s", timestamp, signatureNonce, platform, Constant.APPSECRET)));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    /**
     * 测试appkey错误
     */
    @Test
    public void signature_error3() {
        String number = "13699246974";
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        HttpHeaders headers = new HttpHeaders();
        String timestamp = DateUtil.dateToString(new Date(), "YYYY-MM-dd'T'hh:mm:ss'Z'");
        String signatureNonce = RandomStringUtil.getRandomString(18);
        String platform = "1";
        headers.add("appKey", Constant.APPKEY + "a");
        headers.add("timestamp", timestamp);
        headers.add("signatureNonce", signatureNonce);
        headers.add("platform", platform);
        headers.add("signature", MD5.md5(String.format("timestamp=%s&signatureNonce=%s&platform=%s&appSecret=%s", timestamp, signatureNonce, platform, Constant.APPSECRET)));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    /**
     * 测试签名错误
     */
    @Test
    public void signature_error4() {
        String number = "13699246974";
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        HttpHeaders headers = new HttpHeaders();
        String timestamp = DateUtil.dateToString(new Date(), "YYYY-MM-dd'T'hh:mm:ss'Z'");
        String signatureNonce = RandomStringUtil.getRandomString(18);
        String platform = "1";
        headers.add("appKey", Constant.APPKEY);
        headers.add("timestamp", timestamp);
        headers.add("signatureNonce", signatureNonce);
        headers.add("platform", platform);
        headers.add("signature", MD5.md5(String.format("timestamp=%s&signatureNonce=%s&platform=%s&appSecret=%sa", timestamp, signatureNonce, platform, Constant.APPSECRET)));
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody());
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }
}
