package cn.com.open.apptoolservice;

import cn.com.open.apptoolservice.app.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/test.properties")
public class DnotdeletTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * 测试健康检查
     */
    @Test
    public void dnotdelet() {
        String url = "http://localhost:" + port + "/dnotdelet/mom.html";
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        Assert.assertNotNull(result.getBody());
    }

}