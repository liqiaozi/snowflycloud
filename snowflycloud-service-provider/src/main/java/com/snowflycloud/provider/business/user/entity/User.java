package com.snowflycloud.provider.business.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/23 19:08
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private Integer age;

    @Column(name = "create_time")
    private Date createTime;
}
