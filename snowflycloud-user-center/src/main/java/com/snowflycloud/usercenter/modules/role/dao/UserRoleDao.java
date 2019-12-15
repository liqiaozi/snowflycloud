package com.snowflycloud.usercenter.modules.role.dao;

import com.snowflycloud.usercenter.modules.role.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserRoleDao
 * @Description 用户角色查询
 * @Author snowflying
 * @Date 2019/12/15 17:04
 * @Version 1.0
 **/
@Repository
public interface UserRoleDao extends JpaRepository<UserRole, String> {


}
