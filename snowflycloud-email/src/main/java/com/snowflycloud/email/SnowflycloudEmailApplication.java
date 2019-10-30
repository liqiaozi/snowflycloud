package com.snowflycloud.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@ServletComponentScan(value = "com.snowflycloud")
@EnableFeignClients
@EnableDiscoveryClient
@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties
public class SnowflycloudEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowflycloudEmailApplication.class, args);
        System.out.println("======== SnowflycloudEmailApplication 成功启动 ===========");
        System.out.println("======== SnowflycloudEmailApplication 成功启动 ===========");
        System.out.println("======== SnowflycloudEmailApplication 成功启动 ===========");
    }

}
