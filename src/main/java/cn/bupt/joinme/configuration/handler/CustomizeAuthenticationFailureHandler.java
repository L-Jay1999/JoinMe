package cn.bupt.joinme.configuration.handler;

import cn.bupt.joinme.response.ResponseResult;
import cn.bupt.joinme.share.ResponseType;
import com.alibaba.fastjson.JSON;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseResult result = null;
        if(e instanceof UsernameNotFoundException) {
            result = new ResponseResult(ResponseType.USER_NOT_EXIST);
        } else if (e instanceof BadCredentialsException) {
            System.out.println("error message" + e.getMessage());
            if (e.getMessage().equals("用户不存在"))
                result = new ResponseResult(ResponseType.USER_NOT_EXIST);
            else
                result = new ResponseResult(ResponseType.USER_CREDENTIAL_ERROR);
        } else{
            result = new ResponseResult(ResponseType.COMMON_FAIL);
        }
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
