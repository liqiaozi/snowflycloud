package com.snowflycloud.email.modules.email.dao;/**
 * Created by xflig on 2019/8/17.
 */

import com.snowflycloud.email.modules.email.domain.EmailAcountElectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: EmailTemplateDao
 * @Description: 用户邮箱选举算法配置持久层
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/17 23:42
 * @Vserion 1.0
 **/
@Repository
public interface EmailAcountElectionDao extends JpaRepository<EmailAcountElectionEntity,String> {

    /**
     * 根据用户和业务查询邮箱用户选举算法
     * @param userId
     * @param business
     * @return
     */
    EmailAcountElectionEntity findByUserIdAndBusiness(String userId, String business);


}
