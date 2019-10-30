package com.snowflycloud.email.modules.email.domain;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @file: EmailAcountConfigEntity
 * @description: 邮箱用户配置
 * @author: lixuefei
 * @create: 2019-09-01 13:29
 * @version: v1.0.0
 */
@Data
@Entity
@Table(name = "email_account_config")
@EntityListeners(AuditingEntityListener.class)
public class EmailAcountConfigEntity implements Serializable {

    private static final long serialVersionUID = 2021200452104067239L;

    @Id
    @Column(name = "id",nullable = false,length = 64)
    private String id;

    /**
     * 管理员账号
     */
    @Column(name = "user_id",nullable = false,length = 64)
    private String userId;

    /**
     * 业务key
     */
    @Column(name = "business",nullable = false,length = 64)
    private String business;

    /**
     * 邮箱服务器host
     */
    @Column(name = "host",nullable = false,length = 64)
    private String host;

    /**
     * 邮箱服务器port
     */
    @Column(name = "port",nullable = false,length = 64)
    private String port;

    /**
     * 协议
     */
    @Column(name = "protocol",nullable = false,length = 64)
    private String protocol;


    /**
     * 发件人
     */
    @Column(name = "username",nullable = false,length = 64)
    private String username;

    /**
     * 邮箱密码
     */
    @Column(name = "password",nullable = false,length = 64)
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nick",nullable = false,length = 64)
    private String nick;

    /**
     * 权重，1-100
     */
    @Column(name = "score",nullable = false)
    private Integer score;


}
