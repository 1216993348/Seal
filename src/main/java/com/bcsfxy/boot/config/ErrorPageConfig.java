package com.bcsfxy.boot.config;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
/**
 * 错误页面
 * @author Administrator
 */
@Configuration
public class ErrorPageConfig implements ErrorViewResolver {
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		/*System.out.println("【status】"+status);
		System.out.println("【model】"+model);*/
		request.setAttribute("errors", model);
		/*if(status.is4xxClientError()) {
			return new ModelAndView("/pages/404");
		}else if(status.is5xxServerError()) {
			return new ModelAndView("500");
		}*/
		return new ModelAndView("errors/404");
	}
}
