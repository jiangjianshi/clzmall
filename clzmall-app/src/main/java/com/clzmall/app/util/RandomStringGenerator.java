package com.clzmall.app.util;

import java.util.Random;

/**
 * Created by bairong on 2018/10/11.
 */
public class RandomStringGenerator {

    /**
     * 获取一定长度的随机字符串
     * @param length
     * @return
     */
    public static String getRandomStringByLength(int length) {
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
