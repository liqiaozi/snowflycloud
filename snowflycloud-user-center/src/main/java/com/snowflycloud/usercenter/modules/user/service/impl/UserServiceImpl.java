package com.snowflycloud.usercenter.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.snowflycloud.common.dto.user.RoleDto;
import com.snowflycloud.common.dto.user.UserDto;
import com.snowflycloud.usercenter.modules.permission.domain.Permission;
import com.snowflycloud.usercenter.modules.permission.service.PermissionService;
import com.snowflycloud.usercenter.modules.role.dao.RoleDao;
import com.snowflycloud.usercenter.modules.role.domain.Role;
import com.snowflycloud.usercenter.modules.user.dao.UserDao;
import com.snowflycloud.usercenter.modules.user.domain.User;
import com.snowflycloud.usercenter.modules.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 **/
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDto queryUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);

            // 查询角色
            Set<Role> roles = roleDao.findRoleByUserId(user.getUserId());

            userDto.setRoles(toRoleDtoSet(roles));

            // 查询权限
            if (!CollectionUtils.isEmpty(roles)) {

                Set<String> roleIds = roles.parallelStream().map(Role::getRoleId).collect(Collectors.toSet());

                Set<Permission> permissionSet = permissionService.findByRoleIds(roleIds);
                if (!CollectionUtils.isEmpty(permissionSet)) {
                    Set<String> permissions = permissionSet.parallelStream().map(Permission::getPermissionId).collect(Collectors.toSet());
                    userDto.setPermissions(permissions);
                }

            }
            logger.info("loginAppUser = {}", JSONObject.toJSONString(userDto));
            return userDto;
        }

        return null;
    }

    public Set<RoleDto> toRoleDtoSet(Set<Role> roleSet) {
        Set<RoleDto> roleDtos = new HashSet<RoleDto>();
        for (Role role : roleSet) {
            RoleDto roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    @Override
    public User queryUserByMobile(String mobile) {
        User user = userDao.findUserByMobile(mobile);
        return user;
    }

    @Override
    public User queryUserByEmail(String email) {
        User user = userDao.findUserByEmail(email);
        return user;
    }
}
