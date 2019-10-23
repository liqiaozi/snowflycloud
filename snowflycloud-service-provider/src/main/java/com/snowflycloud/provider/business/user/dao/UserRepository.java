package com.snowflycloud.provider.business.user.dao;

import com.snowflycloud.provider.business.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @file: UserRepository
 * @description: TODO
 * @author: lixuefei
 * @create: 2019-10-23 19:09
 * @version: v1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM tbl_user WHERE username = :username", nativeQuery = true)
    User findUser2(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "update tbl_user set age =:age", nativeQuery = true)
    int updateAge(@Param("age") Integer age);
}