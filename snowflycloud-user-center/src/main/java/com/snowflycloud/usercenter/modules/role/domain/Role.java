package com.snowflycloud.usercenter.modules.role.domain;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @ClassName Role
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 16:44
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "sys_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Role extends SnowflyBaseEntity {
    private static final long serialVersionUID = 4531652716402068952L;


    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "role_id",length = 32)
    private String roleId;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;

}
