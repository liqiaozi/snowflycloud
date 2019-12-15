package com.snowflycloud.usercenter.modules.permission.domain;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @ClassName Permission
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 17:24
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "sys_permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Permission extends SnowflyBaseEntity {

    private static final long serialVersionUID = -6902421591864151835L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "perm_id")
    private String permissionId;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "perm_name")
    private String permissionName;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "path")
    private String path;

    @Column(name = "component")
    private String component;

    @Column(name = "icon")
    private String icon;

    @Column(name = "perms")
    private String perms;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "open_type")
    private String openType;
}
