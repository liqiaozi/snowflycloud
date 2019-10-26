package com.snowflycloud.modules.system.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName Customer
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 16:37
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "sys_customer")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Customer implements Serializable {
    private static final long serialVersionUID = 4304472422557785934L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "customer_zn_name", nullable = false)
    private String customerZNName;

    @Column(name = "customer_en_name", nullable = false)
    private String customerENName;

}
