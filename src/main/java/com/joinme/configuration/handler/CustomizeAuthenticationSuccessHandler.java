package com.joinme.configuration.handler;

import com.joinme.response.ResponseResult;
import com.joinme.share.ResponseType;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails realUser = (UserDetails)user;
        ResponseResult result = new ResponseResult(ResponseType.SUCCESS);
        if (realUser.getUsername().equals("admin"))
            result.setData("admin");
        else
            result.setData("user");
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
