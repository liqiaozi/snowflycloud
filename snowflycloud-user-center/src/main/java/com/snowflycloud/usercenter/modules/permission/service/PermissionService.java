package com.snowflycloud.usercenter.modules.permission.service;

import com.snowflycloud.usercenter.modules.permission.domain.Permission;

import java.util.Set;

/**
 * @ClassName PermissionService
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 17:48
 * @Version 1.0
 **/
public interface PermissionService {

    Set<Permission> findByRoleIds(Set<String> roleIds);
}
