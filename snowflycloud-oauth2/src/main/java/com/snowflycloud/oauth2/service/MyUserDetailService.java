package com.snowflycloud.oauth2.service;

import com.snowflycloud.common.constant.UserConstant;
import com.snowflycloud.common.dto.user.RoleDto;
import com.snowflycloud.common.dto.user.UserDto;
import com.snowflycloud.oauth2.feignclient.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 *
 **/
@Service(value = "userDetailsService")
public class MyUserDetailService implements UserDetailsService {


    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userFeignClient.queryUserByUsername(username);


        if (userDto == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        }

        if (UserConstant.USER_STATUS_DISABLED.equals(userDto.getStatus())) {
            throw new DisabledException("用户不可用");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // 可用性 :true:可用 false:不可用
        boolean enabled = true;
        // 过期性 :true:没过期 false:过期
        boolean accountNonExpired = true;
        // 有效性 :true:凭证有效 false:凭证无效
        boolean credentialsNonExpired = true;
        // 锁定性 :true:未锁定 false:已锁定
        boolean accountNonLocked = true;

        for (RoleDto role : userDto.getRoles()) {
            //角色必须是ROLE_开头，可以在数据库中设置
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleCode());
            grantedAuthorities.add(grantedAuthority);
            //获取权限
            for (String permission : userDto.getPermissions()) {
                GrantedAuthority authority = new SimpleGrantedAuthority(permission);
                grantedAuthorities.add(authority);
            }
        }

        User user = new User(userDto.getUsername(), userDto.getPassword(),
            enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        return user;
    }
}
