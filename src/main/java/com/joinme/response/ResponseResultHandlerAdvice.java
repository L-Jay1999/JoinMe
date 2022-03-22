package com.joinme.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.joinme.share.ResponseType;

@ControllerAdvice(annotations = BaseResponse.class)
@Slf4j
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        log.info("returnType:"+returnType);
        log.info("converterType:"+converterType);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.APPLICATION_JSON.equals(selectedContentType) || MediaType.APPLICATION_JSON_UTF8.equals(selectedContentType)) {
            if (body instanceof ResponseResult) {
                return body;
            } else {
                ResponseResult responseResult =new ResponseResult(ResponseType.SUCCESS.getCode(),ResponseType.SUCCESS.getMsg(),body);
                return responseResult;
            }
        }
        return body;
    }
}