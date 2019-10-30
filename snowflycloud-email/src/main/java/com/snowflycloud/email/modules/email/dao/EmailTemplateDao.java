package com.snowflycloud.email.modules.email.dao;/**
 * Created by xflig on 2019/8/17.
 */

import com.snowflycloud.email.modules.email.domain.EmailTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: EmailTemplateDao
 * @Description: 用户模板持久层
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/17 23:42
 * @Vserion 1.0
 **/
@Repository
public interface EmailTemplateDao extends JpaRepository<EmailTemplateEntity,String> {

    /**
     * 根据邮件模板key查询用户
     * @param key
     * @return
     */
    EmailTemplateEntity findByKey(String key);

}
