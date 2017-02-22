package com.greenfox.zerdaReader.secure;

import com.greenfox.zerdaReader.domain.User;
import com.greenfox.zerdaReader.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log
@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationTokenProcessingFilter extends OncePerRequestFilter {

    String token;
    @Autowired
    UserService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("break01 / inside myFilter");
//        HttpServletResponse response = (HttpServletResponse) response;
//        HttpServletRequest request = (HttpServletRequest) request;
        log.info("break02 / res,req set");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("break03 / inside options if to set cors preflight");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization,content-type");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        log.info("break05 / before /user matcher if");
        if (!request.getRequestURI().matches("/user(.*)")) {
            token = request.getParameter("token");
            log.info("break06 / got token");
            if (token != null) {
                log.info("break06-1 / token not null");
                User user = service.getUserByToken(token);
                log.info("break06-2 / got user");
                if (user != null){
                    log.info("break06b / user not null- stepped inside if - list Grantedauth");
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user, token, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info(SecurityContextHolder.getContext().toString());
                    log.info("break08 / security context set");
                } else {
                    throw new BadCredentialsException("401");
                }
            } else {
                throw new AuthenticationCredentialsNotFoundException("400");
            }
        }
        filterChain.doFilter(request, response);
        log.info("doFilter req,resp / exit filter");
    }

    private boolean isValidToken() {
        return service.isExistingToken(token);
    }
}

