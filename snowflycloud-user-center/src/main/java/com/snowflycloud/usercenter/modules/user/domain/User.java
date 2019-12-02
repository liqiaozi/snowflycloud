package com.snowflycloud.usercenter.modules.user.domain;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 系统用户
 **/
@Data
@Table(name = "sys_user")
public class User extends SnowflyBaseEntity {

    private static final long serialVersionUID = -5694540241055653940L;

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
