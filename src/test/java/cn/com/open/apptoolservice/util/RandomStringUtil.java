package cn.com.open.apptoolservice.util;

import java.util.Random;

/**
 * 随机字符串
 * Created by guxuyang on 13/07/2017.
 */
public class RandomStringUtil {

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
