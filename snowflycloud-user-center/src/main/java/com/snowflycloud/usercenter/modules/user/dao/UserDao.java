package com.snowflycloud.usercenter.modules.user.dao;

import com.snowflycloud.usercenter.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户持久层
 */
@Repository
public interface UserDao extends JpaRepository<User, String> {

    User findUserByUsername(String username);

    User findUserByMobile(String mobile);

    User findUserByEmail(String email);

}
