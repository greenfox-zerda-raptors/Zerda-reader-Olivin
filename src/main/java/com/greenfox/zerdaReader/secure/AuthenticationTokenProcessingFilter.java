package com.greenfox.zerdaReader.secure;

import com.greenfox.zerdaReader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    String token;
    @Autowired
    UserService service;

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization,content-type");
            response.setStatus(HttpServletResponse.SC_OK);
        }

        if (!request.getRequestURI().matches("/user(.*)")) {
            token = request.getParameter("token");
            if (token != null && isValidToken()) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(service.getUserByToken(token), token, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (token == null) {
                throw new AuthenticationCredentialsNotFoundException("400");
            } else if (!isValidToken()) {
                throw new BadCredentialsException("401");
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isValidToken() {
        return service.isExistingToken(token);
    }
}
