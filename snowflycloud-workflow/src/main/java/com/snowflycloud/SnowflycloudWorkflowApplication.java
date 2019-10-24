package com.snowflycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



@ServletComponentScan(value = "com.snowflycloud")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude={org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class})
public class SnowflycloudWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowflycloudWorkflowApplication.class, args);
    }

}
