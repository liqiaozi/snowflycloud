package com.snowflycloud.usercenter.modules.user.domain;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 系统用户
 **/
@Data
@Entity
@Table(name = "sys_user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class User extends SnowflyBaseEntity {

    private static final long serialVersionUID = -5694540241055653940L;

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 邮箱地址
     */
    @Column(name = "email")
    private String email;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 类型
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 用户来源
     */
    @Column(name = "userSource")
    private Integer userSource;

    /**
     * 描述，备注
     */
    @Column(name = "description")
    private String description;

    /**
     * 部门id
     */
    @Transient
    private String departmentId;

    /**
     * 部门名称
     */
    @Transient
    private String departmentName;

}
