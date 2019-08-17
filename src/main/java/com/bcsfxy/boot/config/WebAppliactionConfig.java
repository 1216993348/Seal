package com.bcsfxy.boot.config;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bcsfxy.util.FileUploadPath;
import com.bcsfxy.util.interceptor.ValidateInterceptor;

/**
 * 拦截器配置Bean
 * @author xhy  
 * @date 2019年1月2日
 */
@Configuration
public class WebAppliactionConfig implements WebMvcConfigurer  {
	@Resource
	private MessageSource source;
	@Override 
	public void addInterceptors(InterceptorRegistry registry) {//拦截器注册处理
		registry.addInterceptor(new ValidateInterceptor(source,true)).addPathPatterns("/**");
	}
	//默认访问页面
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("front/front_index");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
	}
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String fileUploadPath = FileUploadPath.getFileUploadPath();//获取文件SpringBoot jar包同级目录
		if(fileUploadPath != null) {
			System.out.println("fileUploadPath: "+fileUploadPath);
	        registry.addResourceHandler("/temp/seal/**").addResourceLocations("file:"+fileUploadPath+ "temp/seal/");
	        registry.addResourceHandler("/upload/seal/**").addResourceLocations("file:"+fileUploadPath+ "upload/seal/");
	        WebMvcConfigurer.super.addResourceHandlers(registry);
		}
      
    }
	
}
