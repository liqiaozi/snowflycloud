package com.snowflycloud.usercenter.modules.role.dao;

import com.snowflycloud.usercenter.modules.role.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName RolePermission
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 17:47
 * @Version 1.0
 **/
@Repository
public interface RolePermissionDao extends JpaRepository<RolePermission, String> {


}
