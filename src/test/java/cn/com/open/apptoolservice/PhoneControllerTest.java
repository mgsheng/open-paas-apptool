package cn.com.open.apptoolservice;

import cn.com.open.apptoolservice.app.Application;
import cn.com.open.apptoolservice.util.SignatureUtil;
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

/**
 * 手机归属地
 * Created by guxuyang on 12/07/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
public class PhoneControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void attribution() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        String number = "13699246974";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();

        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test
    public void attribution_aliyun() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        String number = "236992469744";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();

        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test //手机号码为空
    public void attribution_error1() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("merchantId", 10001);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();

        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test //商户id为空
    public void attribution_error() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/phone/attribution";
        String number = "13699246974";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number", number);
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();

        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

}
