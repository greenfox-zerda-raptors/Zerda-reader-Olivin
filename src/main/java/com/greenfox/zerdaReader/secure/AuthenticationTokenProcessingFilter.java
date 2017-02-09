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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    String token;
    @Autowired
    UserService service;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        if (!((HttpServletRequest) request).getRequestURI().matches("/user(.*)")) {
            if (request instanceof HttpServletRequest) {
                token = ((HttpServletRequest) request).getParameter("token");
                if (token != null && isValidToken()) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(service.getEmailByToken(token), token, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else if (token == null) {
                    throw new AuthenticationCredentialsNotFoundException("400");
                } else if (!isValidToken()) {
                    throw new BadCredentialsException("401");
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isValidToken() {
        return service.isExistingToken(token);
    }
}
