package com.snowflycloud.email.config;/**
 * Created by xflig on 2019/8/18.
 */


import com.snowflycloud.email.modules.email.dao.EmailTemplateDao;
import com.snowflycloud.email.modules.email.domain.EmailTemplateEntity;
import freemarker.cache.TemplateLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @ClassName: MailTemplateLoader
 * @Description: 自定义Freemarker模板加载器
 * @Author: Writen By Mr.Li Xuefei.
 * @Date: 2019/8/18 12:27
 * @Vserion 1.0
 **/
@Component
public class MailTemplateLoader implements TemplateLoader {
    private static final Logger logger = LoggerFactory.getLogger(MailTemplateLoader.class);

    @Autowired
    private EmailTemplateDao emailTemplateDao;

    @Override
    public Object findTemplateSource(String name) throws IOException {
        EmailTemplateEntity template = emailTemplateDao.findByKey(name);

        return new StringTemplateSource(name, template.getContent(), template.getUpdateTime().getTime());
    }

    @Override
    public long getLastModified(Object templateSource) {
        return ((StringTemplateSource) templateSource).lastModified;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader(((StringTemplateSource) templateSource).source);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }

    private static class StringTemplateSource {
        private final String name;
        private final String source;
        private final long lastModified;

        StringTemplateSource(String name, String source, long lastModified) {
            if (name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (source == null) {
                throw new IllegalArgumentException("source == null");
            }
            if (lastModified < -1L) {
                throw new IllegalArgumentException("lastModified < -1L");
            }
            this.name = name;
            this.source = source;
            this.lastModified = lastModified;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof StringTemplateSource) {
                return lastModified == ((StringTemplateSource) obj).lastModified;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

    }

    @Override
    public String toString() {
        return "MailTemplateLoader{" +
                "emailTemplateDao=" + emailTemplateDao +
                '}';
    }
}
