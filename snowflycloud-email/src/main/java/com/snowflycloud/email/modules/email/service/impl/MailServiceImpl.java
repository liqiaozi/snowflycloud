package com.snowflycloud.email.modules.email.service.impl;/**
 * Created by xflig on 2019/8/31.
 */

import com.alibaba.fastjson.JSONObject;
import com.snowflycloud.common.bean.ResultResponse;
import com.snowflycloud.email.config.FreemarkerTransformEngine;
import com.snowflycloud.email.modules.email.domain.EmailRequest;
import com.snowflycloud.email.modules.email.service.EmailSenderProcessor;
import com.snowflycloud.email.modules.email.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

/**
 * @ClassName: MailServiceImpl
 * @Description: TODO
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/31 23:08
 * @Vserion 1.0
 **/
@Service
public class MailServiceImpl implements IMailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private FreemarkerTransformEngine freemarkerTransformEngine;

    @Autowired
    private EmailSenderProcessor emailSenderProcessor;

    @Autowired
    private JavaMailSender mailSender;


    @Value("${mail.from.addr}")
    private String from;


    @Override
    public ResultResponse sendSimpleMail(EmailRequest request) {

        JavaMailSenderImpl javaMailSender = emailSenderProcessor.createMailSender(request.getUserId(), request.getBusiness());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(javaMailSender.getUsername());
        message.setTo(request.getTo().toArray(new String[request.getTo().size()]));
        if(!CollectionUtils.isEmpty(request.getCc())){
            message.setCc(request.getCc().toArray(new String[request.getCc().size()]));
        }
        if(!CollectionUtils.isEmpty(request.getBcc())){
            message.setBcc(request.getBcc().toArray(new String[request.getBcc().size()]));
        }

        message.setSubject(request.getSubject());
        message.setText(request.getContent());
        try {
            this.mailSender.send(message);
            logger.info("[sendSimpleMail] ### 发送简单邮件成功....");
        } catch (Exception e) {
            logger.error("[sendSimpleMail] ### 发送简单邮件异常....",e.getMessage());
            e.printStackTrace();
        }
        return ResultResponse.ok();
    }

    @Override
    public ResultResponse sendHtmlMail(EmailRequest request) {
        JavaMailSenderImpl javaMailSender = emailSenderProcessor.createMailSender(request.getUserId(), request.getBusiness());
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = buildMimeMessageHelper( request, message);
            helper.setFrom(javaMailSender.getUsername());
            helper.setText(request.getContent(),true);
            mailSender.send(message);
            logger.info("[sendHtmlMail] ### 发送Html邮件成功....");
        }catch (Exception e){
            logger.error("[sendHtmlMail] ### 发送Html邮件异常....",e.getMessage());
            e.printStackTrace();
        }
        return ResultResponse.ok();
    }

    @Override
    public ResultResponse sendAttachmentsMail(EmailRequest request) {
        JavaMailSenderImpl javaMailSender = emailSenderProcessor.createMailSender(request.getUserId(), request.getBusiness());
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = buildMimeMessageHelper( request, message);
            helper.setFrom(request.getFrom());
            helper.setText(request.getContent(),true);

            // 处理附件
            Map<String, String> attachments = request.getAttachments();
            if(attachments != null && attachments.size() > 0){
                attachments.entrySet().iterator().forEachRemaining(item -> {
                    String attachmentName = item.getKey();
                    FileSystemResource file = new FileSystemResource(new File(item.getValue()));
                    try {
                        helper.addAttachment(attachmentName,file);
                    } catch (MessagingException e) {
                        logger.info("[sendAttachmentsMail] ### 处理附件异常....数据信息 = {}", JSONObject.toJSONString(item));
                        e.printStackTrace();
                    }
                });
            }

            mailSender.send(message);
            logger.info("[sendAttachmentsMail] ### 发送带附件邮件成功....");
        }catch (Exception e){
            logger.error("[sendAttachmentsMail] ### 发送带附件邮件异常....",e.getMessage());
            e.printStackTrace();
        }

        return ResultResponse.ok();
    }

    @Override
    public ResultResponse sendInlineResourceMail(EmailRequest request) {
        JavaMailSenderImpl javaMailSender = emailSenderProcessor.createMailSender(request.getUserId(), request.getBusiness());
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = buildMimeMessageHelper( request, message);
            helper.setFrom(javaMailSender.getUsername());
            helper.setText(request.getContent(),true);

            // 处理附件
            Map<String, String> inlineResources = request.getInlineResources();
            if(inlineResources != null && inlineResources.size() > 0){
                inlineResources.entrySet().iterator().forEachRemaining(item -> {
                    String resourceId = item.getKey();
                    FileSystemResource file = new FileSystemResource(new File(item.getValue()));
                    try {
                        helper.addAttachment(resourceId,file);
                    } catch (MessagingException e) {
                        logger.info("[sendInlineResourceMail] ### 处理嵌入静态资源的信息异常....数据信息 = {}", JSONObject.toJSONString(item));
                        e.printStackTrace();
                    }
                });
            }

            mailSender.send(message);
            logger.info("[sendInlineResourceMail] ### 发送带嵌入静态资源邮件成功....");
        }catch (Exception e){
            logger.error("[sendInlineResourceMail] ### 发送带嵌入静态资源邮件异常....",e.getMessage());
            e.printStackTrace();
        }

        return ResultResponse.ok();
    }

    @Override
    public ResultResponse sendTemplateMail(EmailRequest request) {
        logger.info("start to send template email....");
        JavaMailSenderImpl javaMailSender = emailSenderProcessor.createMailSender(request.getUserId(), request.getBusiness());
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = buildMimeMessageHelper( request, message);
            helper.setFrom(javaMailSender.getUsername());
            Map<String, Object> variables = request.getTemplateVariables();
            String text = freemarkerTransformEngine.processTemplateToString(request.getTemplateKey(), variables);
            helper.setText(text, true);
            mailSender.send(message);

        } catch (Exception e) {
            logger.error("[sendInlineResourceMail] ### 发送模板邮件异常....",e.getMessage());
            e.printStackTrace();
        }

        logger.info("[sendInlineResourceMail] ### 发送模板邮件成功....");
        return ResultResponse.ok();
    }

    /**
     * 组装参数
     * @param request
     * @param message
     * @throws MessagingException
     */
    private MimeMessageHelper buildMimeMessageHelper(EmailRequest request, MimeMessage message ) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(request.getTo().toArray(new String[request.getTo().size()]));
        if(!CollectionUtils.isEmpty(request.getCc())){
            helper.setCc(request.getCc().toArray(new String[request.getCc().size()]));
        }
        if(CollectionUtils.isEmpty(request.getBcc())){
            helper.setBcc(request.getBcc().toArray(new String[request.getBcc().size()]));
        }
        helper.setSubject(request.getSubject());

        return helper;
    }
}
