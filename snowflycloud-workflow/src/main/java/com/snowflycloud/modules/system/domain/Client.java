package com.snowflycloud.modules.system.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Client
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 16:40
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "sys_client")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Client implements Serializable {

    private static final long serialVersionUID = 4415440220101021699L;


    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "clent_id", nullable = false)
    private String clentId;

    @Column(name = "client_cn_name", nullable = false)
    private String clientCNName;

    @Column(name = "client_en_name", nullable = false)
    private String clientENName;

    @Column(name = "home_url")
    private String homeUrl;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Transient
    private List<BusinessCategory> categoryList;
}
