package com.clzmall.app.util;

import com.clzmall.common.common.WxConsts;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bairong on 2018/10/11.
 */
@Slf4j
public class HttpRequest {

    private static final int socketTimeout = 10000;
    //传输超时时间，默认30秒
    private static final int connectTimeout = 30000;

    /**
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public static String sendPost(String url, Map<String, String> map, String key) throws Exception {
        HttpPost httpPost = new HttpPost(url);        //解决XStream对出现双下划线的bug
        String postDataXML = WXPayUtil.generateSignedXml(map, key);
        log.info("转换xml：{}", postDataXML);
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        HttpClient httpClient = HttpClients.createDefault();
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;
    }

    // 使用POST方法发送XML数据
    public static String sendXMLDataByPost(String url, Map<String, String> map, String key) throws Exception {
        HttpClient  client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "text/xml");
        String postDataXML = WXPayUtil.generateSignedXml(map, key);
        log.info("转换xml：{}", postDataXML);
        post.setEntity(new StringEntity(postDataXML, "UTF-8"));
        HttpResponse response = client.execute(post);
        System.out.println(response.toString());
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, "UTF-8");
        return result;

    }

    /**
     * 自定义证书管理器，信任所有证书	 * @author pc	 *
     */
    public static class MyX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    }

}
