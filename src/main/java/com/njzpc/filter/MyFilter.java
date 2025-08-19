package com.njzpc.filter;/*
 * @author Jiang longteng
 * @date 2025/7/22 9:40
 * */

/*过滤器，可以说过滤url请求，自定义响应*/

import lombok.extern.log4j.Log4j;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebFilter(urlPatterns = "/api/*")
@Log4j
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.debug("url: " + request.getRequestURI());
        String uri = request.getRequestURI();
        log.debug("uri: " + uri);

        //所有非登陆相关操作，必须登录
        Map m = (Map)request.getSession().getAttribute("isLogin");
        log.debug("登陆状态m: " + m);

        if (m == null) {
            servletResponse.getWriter().print("noLogin");
            return;
        }

        else {
            String userType = (String)m.get("userType");
            switch (userType){
                case "admin": break;
                case "seller":

                    break;
                case "customer": break;
            }
        }

        filterChain.doFilter(request,servletResponse);
    }

}
