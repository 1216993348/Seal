package com.bcsfxy.boot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
@Component
public class BackLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response =(HttpServletResponse) servletResponse;
        Object obj = request.getSession().getAttribute("admin");
        //System.out.println(obj);
        //System.out.println(request.getSession().getId());
        if(obj != null) {
        	request.setCharacterEncoding("UTF-8");
        	response.setCharacterEncoding("UTF-8");
        	filterChain.doFilter(servletRequest,servletResponse);
        }else {
        	request.getRequestDispatcher("/backLogin").forward(request, response);
        }
    	
    }
    @Override
    public void destroy() {

    }
}
