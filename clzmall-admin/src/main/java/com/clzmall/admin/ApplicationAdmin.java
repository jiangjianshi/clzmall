package com.clzmall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
// @EnableAsync
// @EnableTransactionManagement
// @EnableScheduling
// @PropertySource(value = {"classpath:application-dev.properties"})
public class ApplicationAdmin implements WebMvcConfigurer {


	public static void main(String[] args) {

		SpringApplication.run(ApplicationAdmin.class, args);
	}

}
