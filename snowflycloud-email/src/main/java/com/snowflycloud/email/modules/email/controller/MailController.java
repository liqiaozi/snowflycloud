package com.snowflycloud.email.modules.email.controller;/**
 * Created by xflig on 2019/8/18.
 */


import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.email.modules.email.domain.EmailRequest;
import com.snowflycloud.email.modules.email.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MailController
 * @Description: 邮件服务管理
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/18 0:23
 * @Vserion 1.0
 **/
@RestController
@RequestMapping(value  = "/mail/v1")
public class MailController {

    @Autowired
    private IMailService mailService;

    /**
     * 发送简单邮件
     * @param emailRequest
     * @return
     */
    @PostMapping(value = "/send/simple")
    public ResultResponse sendSimpleEmail(@RequestBody EmailRequest emailRequest){
        ResultResponse response = mailService.sendSimpleMail(emailRequest);
        return response;
    }

    /**
     * 发送简单邮件
     * @param emailRequest
     * @return
     */
    @PostMapping(value = "/send/template")
    public ResultResponse sendTemplateEmail(@RequestBody EmailRequest emailRequest){
        ResultResponse response = mailService.sendTemplateMail(emailRequest);
        return response;
    }





}
