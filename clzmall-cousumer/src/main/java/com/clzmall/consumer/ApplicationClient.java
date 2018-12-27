package com.clzmall.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by jiangjianshi on 18/12/6.
 */
//@RibbonClient(name="clzmall-app")
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages= {"com.clzmall"})
@ComponentScan("com.clzmall")
public class ApplicationClient {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationClient.class, args);
    }
}
