package com.nova.aathif.taxibookingapi.config;

import com.nova.aathif.taxibookingapi.dto.RequestMetaDTO;
import com.nova.aathif.taxibookingapi.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptorConfig implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    RequestMetaDTO requestMetaDTO;

    public JwtInterceptorConfig(RequestMetaDTO requestMetaDTO){
        this.requestMetaDTO = requestMetaDTO;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader("authorization");
        if (!(request.getRequestURI().contains("rider/auth/"))){
            Claims claims = jwtUtil.verifyToken(authorization);
            requestMetaDTO.setRiderId(Integer.valueOf(claims.getIssuer()));
            requestMetaDTO.setName(claims.get("name").toString());
            requestMetaDTO.setMobile(claims.get("mobile").toString());
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);

    }
}
