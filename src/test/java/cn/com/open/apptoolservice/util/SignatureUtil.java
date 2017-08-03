package cn.com.open.apptoolservice.util;

import cn.com.open.apptoolservice.app.utils.DateUtil;
import cn.com.open.apptoolservice.app.utils.MD5;
import org.springframework.http.HttpHeaders;

import java.util.Date;

/**
 * 签名头
 * Created by guxuyang on 13/07/2017.
 */
public class SignatureUtil {

    public static HttpHeaders getHeaders(String appKey, String appSecret) {
        HttpHeaders headers = new HttpHeaders();
        String timestamp = DateUtil.dateToString(new Date(), "YYYY-MM-dd'T'hh:mm:ss'Z'");
        String signatureNonce = RandomStringUtil.getRandomString(18);
        String platform = "1";
        headers.add("appKey", appKey);
        headers.add("timestamp", timestamp);
        headers.add("signatureNonce", signatureNonce);
        headers.add("platform", platform);
        headers.add("signature", MD5.md5(String.format("timestamp=%s&signatureNonce=%s&platform=%s&appSecret=%s", timestamp, signatureNonce, platform, appSecret)));
        return headers;
    }

}
