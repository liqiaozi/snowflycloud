package com.snowflycloud.usercenter.modules.role.domain;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @ClassName RolePermission
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 17:45
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "sys_role_permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class RolePermission extends SnowflyBaseEntity {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "perm_id")
    private String permissionId;
}


