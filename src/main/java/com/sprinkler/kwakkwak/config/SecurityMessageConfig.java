package com.sprinkler.kwakkwak.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class SecurityMessageConfig {

    @Bean
    public MessageSource messageSource() {
        Locale.setDefault(Locale.KOREA);
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasenames("classpath:message/security_message", "classpath.org/springframework/security/messages");
        return messageSource;
    }
}
