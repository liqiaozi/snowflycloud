package com.snowflycloud.usercenter.modules.role.dao;

import com.snowflycloud.usercenter.modules.role.domain.Role;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * 用户持久层
 */
@Repository
public interface RoleDao extends JpaRepository<Role, String> {


    @Query(value = "select r.* from sys_user_role ur inner join sys_role r on r.role_id = ur.role_id where ur.user_id =:userId", nativeQuery = true)
    Set<Role> findRoleByUserId(@Param("userId") String userId);
}
