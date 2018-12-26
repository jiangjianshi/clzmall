package com.clzmall.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by jiangjianshi on 18/12/26.
 */
@SpringBootApplication
@EnableEurekaServer // EurekaServer服务器端启动类,接受其它微服务注册进来
public class ApplicationRegister {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRegister.class, args);
    }
}