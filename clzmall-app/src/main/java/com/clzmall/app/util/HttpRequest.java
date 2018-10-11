package com.clzmall.app.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * Created by bairong on 2018/10/11.
 */
@Slf4j
public class HttpRequest {

    private static final int socketTimeout = 10000;
    //传输超时时间，默认30秒
    private static final int connectTimeout = 30000;

    /**
     * post请求
     *
     * @param url
     * @param xmlObj
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     */
    public static String sendPost(String url, Object xmlObj) throws ClientProtocolException, IOException, UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        HttpPost httpPost = new HttpPost(url);        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("__", "_")));
        xStreamForRequestPostData.alias("xml", xmlObj.getClass());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
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
