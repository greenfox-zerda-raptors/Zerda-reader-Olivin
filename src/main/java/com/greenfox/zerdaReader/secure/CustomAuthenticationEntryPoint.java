package com.greenfox.zerdaReader.secure;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        if (authException.getMessage().equals("400")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No authentication token is provided, please refer to the API specification");
        } else if (authException.getMessage().equals("401")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The provided authentication token is not valid.");
        }
    }
}
