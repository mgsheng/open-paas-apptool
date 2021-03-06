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
 * 个人三大运营商手机号码验证
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
public class MobileVerifyControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void verify_1() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246974");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test
    public void verify_tcl() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246973");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test
    public void verify_tcl_error() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "1369924697");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test
    public void verify() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246974");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("type", "aliyun");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test
    public void verify_2() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "17610495185");
        param.add("idCard", "152628198206300034");
        param.add("realName", "宋岩");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("type", "aliyun");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test
    public void verifyError() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246973");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("type", "aliyun");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test //type错误
    public void verifyError_1() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246973");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("type", "hehe");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test
    public void verifyError_2() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        //param.add("number",  "13699246973");
        //param.add("idCard", "410329199312211517");
        //param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("type", "hehe");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test
    public void verifyError_3() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/verify";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        //param.add("number",  "13699246973");
        //param.add("idCard", "410329199312211517");
        //param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test
    public void mobileModifyVerifyResult() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/modifyVerifyResult";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246974");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("verifyResult", "1");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(1, status);
    }

    @Test
    public void mobileModifyVerifyResultError1() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/modifyVerifyResult";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246973");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("verifyResult", "92");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test
    public void mobileModifyVerifyResultError2() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/modifyVerifyResult";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246973");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("verifyResult", "92");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }

    @Test
    public void mobileModifyVerifyResultError3() {
        String url =  "http://localhost:" + port + "/api/apptoolservice/v1/mobile/modifyVerifyResult";
        HttpHeaders headers = SignatureUtil.getHeaders(Constant.APPKEY, Constant.APPSECRET);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("number",  "13699246973");
        param.add("idCard", "410329199312211517");
        param.add("realName", "谷旭阳qq");
        param.add("sourceUid", "10000");
        param.add("sourceUserName", "李云龙");
        param.add("merchantId", "10001");
        param.add("verifyResult", "-1");
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        String json = responseEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(json);
        int status = jsonObject.getInteger("status");
        Assert.assertEquals(0, status);
    }
}
