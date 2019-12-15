package com.snowflycloud.usercenter.modules.permission.service.impl;

import com.snowflycloud.usercenter.modules.permission.dao.PermissionDao;
import com.snowflycloud.usercenter.modules.permission.domain.Permission;
import com.snowflycloud.usercenter.modules.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @ClassName PermissionServiceImpl
 * @Description TODO
 * @Author snowflying
 * @Date 2019/12/15 17:49
 * @Version 1.0
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;


    @Override
    public Set<Permission> findByRoleIds(Set<String> roleIds) {
        return permissionDao.findPermissionByRoleIds(roleIds);
    }
}
