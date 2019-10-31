package com.snowflycloud.email.config;/**
 * Created by xflig on 2019/8/17.
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: MailProperties
 * @Description: TODO
 * @Author: Writen By Mr.Li Xuefei.
 * @Vserion 1.0
 **/
@Component
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties {
    private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String protocol = "smtp";

    private Charset defaultEncoding = DEFAULT_CHARSET;

    private Map<String, String> properties = new HashMap<String, String>();
    private String jndiName;
    private boolean testConnection;

}
