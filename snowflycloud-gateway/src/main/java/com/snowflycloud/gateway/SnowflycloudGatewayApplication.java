package com.snowflycloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class SnowflycloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowflycloudGatewayApplication.class, args);

        System.out.println("==========================================================================");
        System.out.println("=============== SnowflycloudGatewayApplication 启动成功 ===================");
        System.out.println("==========================================================================");
    }

}
