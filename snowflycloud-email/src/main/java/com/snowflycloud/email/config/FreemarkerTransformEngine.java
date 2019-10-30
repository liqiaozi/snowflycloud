package com.snowflycloud.email.config;/**
 * Created by xflig on 2019/8/18.
 */

import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName: FreemarkerTransformEngine.java
 * @Description: 自定义Freemarker模板引擎
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/18 20:26
 * @Vserion 1.0
 **/
@Component
public class FreemarkerTransformEngine implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerTransformEngine.class);

    @Autowired
    private Configuration configuration;

    @Autowired
    private MailTemplateLoader mailTemplateLoader;

    /**
     * 初始化配置.
     */
    private void initConfiguration(){
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.CHINA);
        configuration.setNumberFormat("#");
        TemplateLoader[] loaders = new TemplateLoader[]{mailTemplateLoader};
        MultiTemplateLoader templateLoader = new MultiTemplateLoader(loaders);
        configuration.setTemplateLoader(templateLoader);
    }

    /**
     * 解析模板文件.
     * @param templateName 模板名称
     * @param variables 参数信息
     * @return
     */
    public String processTemplateToString(String templateName,Map<String,Object> variables){
        Template template = this.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        if(template == null){
            return "";
        }
        try {
            template.process(variables,writer);
        } catch (TemplateException | IOException e) {
            logger.error("transformToString exception");
            e.printStackTrace();
        }

        return writer.toString();

    }

    public Template getTemplate(String templateName){

        try {
            return configuration.getTemplate(templateName);
        } catch (IOException e) {
            logger.error("getTemplate exception.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initConfiguration();
    }
}
