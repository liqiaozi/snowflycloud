package com.snowflycloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableOAuth2Sso
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.snowflycloud.gateway")
public class SnowflycloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowflycloudGatewayApplication.class, args);

        System.out.println("==========================================================================");
        System.out.println("=============== SnowflycloudGatewayApplication 启动成功 ===================");
        System.out.println("==========================================================================");
    }

}
