package com.snowflycloud.modules.system.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ClassName BusinessCategory
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/26 16:43
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "sys_business_category")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BusinessCategory implements Serializable {
    private static final long serialVersionUID = 536705778860298687L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "business_id", nullable = false)
    private String businessId;

    @Column(name = "business_name", nullable = false)
    private String businessName;

    @Column(name = "sort", nullable = false)
    private Integer sort;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "client_id", nullable = false)
    private String clientId;
}
