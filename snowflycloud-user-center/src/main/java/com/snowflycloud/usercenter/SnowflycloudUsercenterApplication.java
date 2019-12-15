package com.snowflycloud.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.snowflycloud"})
public class SnowflycloudUsercenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowflycloudUsercenterApplication.class, args);
    }

}
