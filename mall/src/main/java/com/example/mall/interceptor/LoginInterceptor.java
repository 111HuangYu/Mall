package com.example.mall.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         if (request.getSession().getAttribute("userId")==null){
             response.sendRedirect(request.getContextPath()+"/login.html");
             //拦截请求
             return false;
         }else {
             //不拦截,放行
             return true;
         }
    }
}
