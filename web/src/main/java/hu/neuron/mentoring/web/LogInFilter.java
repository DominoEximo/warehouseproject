package hu.neuron.mentoring.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter(value = "/secured/*" , urlPatterns = "/loginFilter")
public class LogInFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            String action = req.getServletPath();

            if("/".equals(action) || "/login.html".equals(action)){
                chain.doFilter(request,response);
                System.out.println("asd");
            }else {
                Object isAutenticated = req.getSession().getAttribute("authenticated");
                if(isAutenticated != null){
                    boolean isLoggedIn = (Boolean) isAutenticated;
                    if(isLoggedIn){
                        chain.doFilter(request,response);
                        return;
                    }
                }
            }
            res.sendRedirect("/login.html");
    }

    public void destroy() {
    }

}
