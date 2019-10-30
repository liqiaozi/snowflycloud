package com.snowflycloud.email.modules.email.dao;/**
 * Created by xflig on 2019/8/17.
 */

import com.snowflycloud.email.modules.email.domain.EmailAcountConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @ClassName: EmailTemplateDao
 * @Description: 邮箱用户配置持久层
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/17 23:42
 * @Vserion 1.0
 **/
@Repository
public interface EmailAcountConfigDao extends JpaRepository<EmailAcountConfigEntity,String> {

    /**
     * 根据用户和业务查询邮箱用户配置
     * @param userId
     * @param business
     * @return
     */
    List<EmailAcountConfigEntity> findByUserIdAndBusiness(String userId, String business);


}
