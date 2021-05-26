package com.lucent.skeleton.security;

import com.google.gson.Gson;
import com.lucent.skeleton.exception.InvalidObjectResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author lucent
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException ae) throws IOException, ServletException {
        InvalidObjectResponse invalidObjectResponse = new InvalidObjectResponse();
        String jsonLoginResponse = new Gson().toJson(invalidObjectResponse);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);
    }

}
