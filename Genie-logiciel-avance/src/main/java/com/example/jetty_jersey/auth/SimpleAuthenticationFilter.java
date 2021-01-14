package com.example.jetty_jersey.auth;


import java.io.IOException;

import javax.annotation.Priority;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.HttpHeaders;

@Priority(Priorities.AUTHENTICATION)
public class SimpleAuthenticationFilter implements Filter {
	
    @Override
    public void destroy() {
    }

    private void fail(HttpServletResponse httpResp) throws IOException {
        httpResp.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"");
        httpResp.sendError(HttpServletResponse.SC_FORBIDDEN, "Page requires login.");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        String path = request.getRequestURL().toString();
        String method = request.getMethod();
        
        System.out.println("ON LIT : "+ method + " " + path);
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
