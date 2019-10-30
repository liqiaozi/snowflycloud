package com.snowflycloud.email.modules.email.service;/**
 * Created by xflig on 2019/8/31.
 */


import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.email.modules.email.domain.EmailRequest;

/**
 * @ClassName: MailService
 * @Description:邮件服务接口.
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/31 23:01
 * @Vserion 1.0
 **/
public interface IMailService {

    /**
     * 发送纯文本的简单邮件.
     * @param emailRequest
     * @return
     */
    ResultResponse sendSimpleMail(EmailRequest emailRequest);

    /**
     * 发送html格式的邮件.
     * @param emailRequest
     * @return
     */
    ResultResponse sendHtmlMail(EmailRequest emailRequest);

    /**
     * 发送带附件的邮件.
     * @param emailRequest
     * @return
     */
    ResultResponse sendAttachmentsMail(EmailRequest emailRequest);

    /**
     * 发送嵌入静态资源（一般是图片）的邮件
     * @param emailRequest
     * @return
     */
    ResultResponse sendInlineResourceMail(EmailRequest emailRequest);

    /**
     * 发送模板邮件
     * @param emailRequest
     * @return
     */
    ResultResponse sendTemplateMail(EmailRequest emailRequest);


}
