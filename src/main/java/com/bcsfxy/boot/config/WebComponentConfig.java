package com.bcsfxy.boot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bcsfxy.boot.filter.BackLoginFilter;

@Configuration
public class WebComponentConfig {
    @Bean
    public FilterRegistrationBean<BackLoginFilter> someFilterRegistration1() {
        //新建过滤器注册类
        FilterRegistrationBean<BackLoginFilter> registration = new FilterRegistrationBean<BackLoginFilter>();
        // 添加我们写好的过滤器
        registration.setFilter( new BackLoginFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/back/*","/back/actions/*");
        return registration;
    }
}
