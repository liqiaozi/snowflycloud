package com.snowflycloud.email.modules.email.domain;/**
 * Created by xflig on 2019/8/17.
 */

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: EmailTemplateEntity
 * @Description: 模板邮件实体
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/17 23:38
 * @Vserion 1.0
 **/
@Data
@Entity
@Table(name = "email_template")
@EntityListeners(AuditingEntityListener.class)
public class EmailTemplateEntity implements Serializable {
    private static final long serialVersionUID = 3832513720564138668L;

    @Id
    @Column(name = "id",nullable = false,length = 64)
    private String key;

    @Column(name = "user_id",nullable = false,length = 64)
    private String userId;

    @Column(name = "business",nullable = false,length = 64)
    private String business;

    @Column(name = "name",nullable = false,length = 64)
    private String name;

    @Lob
    @Column(name = "content",columnDefinition = "text")
    private String content;

    @Column(name = "version")
    private String version;

    @Column(name = "status")
    private Integer status;

    @Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private Date updateTime;


}
