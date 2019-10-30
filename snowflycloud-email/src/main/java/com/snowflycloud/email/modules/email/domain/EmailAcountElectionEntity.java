package com.snowflycloud.email.modules.email.domain;

import com.snowflycloud.email.modules.email.enums.AcountElectionAlgorithmEnum;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @file: EmailAcountElectionEntity
 * @description: 邮箱选举算法配置
 * @author: lixuefei
 * @create: 2019-09-01 14:01
 * @version: v1.0.0
 */
@Data
@Entity
@Table(name = "email_account_election")
@EntityListeners(AuditingEntityListener.class)
public class EmailAcountElectionEntity implements Serializable {

    @Id
    @Column(name = "id",nullable = false,length = 64)
    private String id;

    /**
     * 管理员
     */
    @Column(name = "user_id",nullable = false,length = 64)
    private String userId;

    /**
     * 业务
     */
    @Column(name = "business",nullable = false,length = 64)
    private String business;

    /**
     * 邮箱用户选举算法
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "election",nullable = false)
    private AcountElectionAlgorithmEnum electionAlgorithmEnum;
}
