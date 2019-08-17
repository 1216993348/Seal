package com.bcsfxy.boot.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 全局异常配置类
 * @author Administrator
 */
public class GlobalExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "errors/404";//定义错误显示页
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public Object defaultErrorHandler(Exception e) {//出现异常后会跳转到此方法
		return e.toString(); 
	}
}
