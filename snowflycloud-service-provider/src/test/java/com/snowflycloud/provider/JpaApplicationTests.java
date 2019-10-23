package com.snowflycloud.provider;

import com.snowflycloud.provider.business.user.dao.UserRepository;
import com.snowflycloud.provider.business.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @ClassName JpaApplicationTests
 * @Description TODO
 * @Author lixuefei
 * @Date 2019/10/23 19:10
 * @Version 1.0
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = SnowflycloudServiceProviderApplication.class)
public class JpaApplicationTests {

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testJPA() {
        userRepository.deleteAll();

        User user = new User(11L,"mengday", 28, new Date());
        userRepository.save(user);

        Page<User> userPage = userRepository.findAll(PageRequest.of(0, 10));

        //User user1 = userRepository.findUser("mengday", 20);
        User user2 = userRepository.findUser2("mengday");

        int result = userRepository.updateAge(18);

        System.out.println(user2);
    }
}