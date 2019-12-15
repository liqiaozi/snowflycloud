package com.snowflycloud.usercenter.modules.permission.dao;

import com.snowflycloud.usercenter.modules.permission.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * @ClassName PermissionDao
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 17:44
 * @Version 1.0
 **/
public interface PermissionDao extends JpaRepository<Permission, String> {


    @Query(value = "select p.* from sys_permission p inner join sys_role_permission rp on rp.perm_id = p.perm_id where rp.role_id in (:roleIds)", nativeQuery = true)
    Set<Permission> findPermissionByRoleIds(@Param(value = "roleIds") Set<String> roleIds);

}
