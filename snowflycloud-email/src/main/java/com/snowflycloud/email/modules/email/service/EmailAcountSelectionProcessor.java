package com.snowflycloud.email.modules.email.service;

import com.snowflycloud.email.modules.email.dao.EmailAcountElectionDao;
import com.snowflycloud.email.modules.email.domain.EmailAcountConfigEntity;
import com.snowflycloud.email.modules.email.domain.EmailAcountElectionEntity;
import com.snowflycloud.email.modules.email.enums.AcountElectionAlgorithmEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @file: EmailAcountSelectionProcessor
 * @description: 邮箱用户配置选举处理器
 * @author: lixuefei
 * @create: 2019-09-01 14:11
 * @version: v1.0.0
 */
@Component
public class EmailAcountSelectionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EmailAcountSelectionProcessor.class);

    @Autowired
    private EmailAcountElectionDao emailAcountElectionDao;

    private final Map<String, Integer> INDEX_MAP = new ConcurrentHashMap<>();

    /**
     * 选举邮箱用户
     * @param emailAcountList
     * @param userId
     * @param business
     * @return
     */
    public EmailAcountConfigEntity selectEmailAcount(List<EmailAcountConfigEntity> emailAcountList, String userId, String business){

        EmailAcountConfigEntity emailAcount = null;

        EmailAcountElectionEntity electionConfig = emailAcountElectionDao.findByUserIdAndBusiness(userId, business);
        if(null != electionConfig){
           AcountElectionAlgorithmEnum algorithmEnum = electionConfig.getElectionAlgorithmEnum();

            logger.info("[EmailAcountSelectionProcessor_selectEmailAcount] algorithmEnum = {}",algorithmEnum.name());
           switch (algorithmEnum){
               case RANDOM:
                   emailAcount = random(emailAcountList);
                   break;
               case ROUNDROBIN:
               case CONSISTENT_HASH:
                   emailAcount = roundRobin(emailAcountList,userId, business);
                   break;

               default:
                   emailAcount = emailAcountList.get(0);
           }
        }
        return emailAcount;

    }

    /**
     * 随机选举
     * @param emailAcountList
     * @return
     */
    public EmailAcountConfigEntity random(List<EmailAcountConfigEntity> emailAcountList){
        Random random = new Random();
        int index = random.nextInt(emailAcountList.size());
        logger.info("[EmailAcountSelectionProcessor_random] index = {}",index);
        return emailAcountList.get(index);
    }

    /**
     * 轮询选举
     * @param emailAcountList
     * @return
     */
    public EmailAcountConfigEntity roundRobin(List<EmailAcountConfigEntity> emailAcountList,String userId,String business){

        EmailAcountConfigEntity emailAcount = null;

        String key = userId + "_" + business;
        synchronized (INDEX_MAP){
            Integer index = INDEX_MAP.get(key);
            if(null == index){
                emailAcount = emailAcountList.get(0);
                index = 0;
            }else if(index > emailAcountList.size() - 1){
                emailAcount = emailAcountList.get(0);
                index = 0;
            }else{
                emailAcount = emailAcountList.get(index);
            }
            logger.info("[EmailAcountSelectionProcessor_roundRobin] index = {}",index);
            index++;
            INDEX_MAP.put(key,index);
        }

        return emailAcount;
    }
}
