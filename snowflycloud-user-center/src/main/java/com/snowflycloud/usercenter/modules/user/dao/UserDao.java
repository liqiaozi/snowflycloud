package com.snowflycloud.usercenter.modules.user.dao;

import com.snowflycloud.usercenter.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface UserDao extends JpaRepository<User, String> {}
