package com.snowflycloud.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.snowflycloud"})
public class SnowflycloudOauth2Application {
    public static void main(String[] args) {
        SpringApplication.run(SnowflycloudOauth2Application.class, args);
    }
}
