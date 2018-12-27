package com.clzmall.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by bairong on 2018/12/27.
 */
@Component
public class RobbinConfig {

    @Bean
    @LoadBalanced
    //Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端  负载均衡的工具。
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
