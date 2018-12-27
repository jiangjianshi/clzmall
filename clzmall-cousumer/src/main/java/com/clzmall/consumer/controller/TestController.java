package com.clzmall.consumer.controller;

import com.clzmall.api.entity.RespMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bairong on 2018/12/27.
 */
@RestController
public class TestController {

    private static final String REST_URL_PREFIX = "http://clzmall-app";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/address/listAddress1")
    public RespMsg listAddress(Integer uid) {

        Map params = new HashMap();
        params.put("uid", uid);
        return restTemplate.getForObject(REST_URL_PREFIX + "/address/listAddress/"+uid, RespMsg.class);
    }
}
