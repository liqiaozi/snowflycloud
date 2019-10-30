package com.snowflycloud.email.modules.email.service;

import com.snowflycloud.email.modules.email.dao.EmailAcountConfigDao;
import com.snowflycloud.email.modules.email.domain.EmailAcountConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Properties;

/**
 * @file: EmailSenderProcessor
 * @description: 邮箱发送器处理类
 * @author: lixuefei
 * @create: 2019-09-01 13:39
 * @version: v1.0.0
 */
@Component
public class EmailSenderProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EmailSenderProcessor.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailAcountConfigDao emailAcountConfigDao;

    @Autowired
    private EmailAcountSelectionProcessor emailAcountSelectionProcessor;

    /**
     * 创建邮件发送器
     *
     * @param userId
     * @param business
     * @return
     */
    public JavaMailSenderImpl createMailSender(String userId, String business) {

        // 查询用户自定义邮箱用户配置
        List<EmailAcountConfigEntity> emailAcountList = emailAcountConfigDao.findByUserIdAndBusiness(userId, business);

        // 如果为空，则使用系统的默认邮箱用户
        if (CollectionUtils.isEmpty(emailAcountList)) {
            logger.error("[EmailSenderProcessor_createMailSender] userId = {} and business = {} 未配置邮箱用户信息....", userId, business);
            return (JavaMailSenderImpl) this.mailSender;
        } else {
            // 根据用户自定义的邮箱用户选择算法，选择出指定的邮箱用户.
            EmailAcountConfigEntity emailAcount = emailAcountSelectionProcessor.selectEmailAcount(emailAcountList, userId, business);
            if (null == emailAcount) {
                logger.error("[EmailSenderProcessor_createMailSender] userId = {} and business = {} 选举邮箱用户为空....", userId, business);
                return (JavaMailSenderImpl) this.mailSender;
            } else {
                // 组装配置
                JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
                javaMailSender.setHost(emailAcount.getHost());
                javaMailSender.setUsername(emailAcount.getUsername());
                javaMailSender.setPassword(emailAcount.getPassword());
                javaMailSender.setProtocol(emailAcount.getProtocol());
                javaMailSender.setDefaultEncoding("utf-8");

                Properties properties = new Properties();
                properties.setProperty("mail.smtp.timeout", 1000 + "");
                properties.setProperty("mail.smtp.auth", "true");
                javaMailSender.setJavaMailProperties(properties);

                return javaMailSender;

            }
        }

    }


}
