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
@Table(name = "sys_user_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UserRole extends SnowflyBaseEntity {
    private static final long serialVersionUID = 4531652716402068952L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private String roleId;

}
