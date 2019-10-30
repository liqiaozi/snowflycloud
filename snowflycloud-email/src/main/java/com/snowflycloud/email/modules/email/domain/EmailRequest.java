package com.snowflycloud.email.modules.email.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EmailEntity
 * @Description: 邮件请求实体
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/17 23:38
 * @Vserion 1.0
 **/
@Data
public class EmailRequest implements Serializable {

    private static final long serialVersionUID = -8427676503834828742L;

    /**
     * 管理员
     */
    private String userId;

    /**
     * 业务
     */
    private String business;

    /**
     * 邮件发送者
     */
    private String from;

    /**
     * 收件人列表
     */
    private List<String> to;

    /**
     * 抄送人列表
     */
    private List<String> cc;

    /**
     * 暗抄人列表
     */
    private List<String> bcc;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件正文,这里以正文为主，模板为辅。
     * 说明：当 content 有值时，会忽略 templateKey 和 variables的模板相关设置
     */
    private String content;

    /**
     * 邮件模板,
     */
    private String templateKey;

    /**
     * 模板内变量
     */
    private Map<String,Object> templateVariables;

    /**
     * 附件信息.
     */
    private Map<String,String> attachments;

    /**
     * 静态资源信息.
     */
    private Map<String,String> inlineResources;


}
