package com.clzmall.app.util;

import com.clzmall.common.common.WxConsts;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by bairong on 2018/10/11.
 */
@Slf4j
public class Signature {


    public static String getSign(Object o) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                String name = f.getName();
                XStreamAlias anno = f.getAnnotation(XStreamAlias.class);
                if (anno != null) name = anno.value();
                list.add(name + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WxConsts.PAY_SECRET;
        log.info("Sign Before MD5:{}", result);
        result =WXPayUtil.MD5(result);
        log.info("Sign Result:{}", result);
        return result;
    }

    public static String getSign(Map<String, Object> map) throws Exception{
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + WxConsts.PAY_SECRET;
        log.info("Sign Before MD5:{}", result);
        result =WXPayUtil.MD5(result);
        log.info("Sign Result:{}", result);
        return result;
    }

}