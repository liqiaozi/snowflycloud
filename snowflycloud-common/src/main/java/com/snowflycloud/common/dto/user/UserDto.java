package com.snowflycloud.common.dto.user;

import com.snowflycloud.common.base.SnowflyBaseEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 系统用户
 **/
@Data
public class UserDto extends SnowflyBaseEntity{

    private static final long serialVersionUID = -5694540241055653940L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 用户来源
     */
    private Integer userSource;

    /**
     * 描述，备注
     */
    private String description;

    /**
     * 部门id
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;


    private Set<RoleDto> roles;

    private Set<String> permissions;

}
