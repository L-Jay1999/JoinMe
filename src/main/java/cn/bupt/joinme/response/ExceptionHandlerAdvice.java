package cn.bupt.joinme.response;

import cn.bupt.joinme.exception.BaseException;
import cn.bupt.joinme.share.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(annotations = BaseResponse.class)
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e) {
        log.error(e.getMessage(),e);
        return new ResponseResult(ResponseType.SERVICE_ERROR.getCode(),ResponseType.SERVICE_ERROR.getMsg(),null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(),e);
        return new ResponseResult(ResponseType.SERVICE_ERROR.getCode(),ResponseType.SERVICE_ERROR.getMsg(),null);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseResult handleBaseException(BaseException e) {
        log.error(e.getMessage(),e);
        ResponseType code=e.getCode();
        return new ResponseResult(code.getCode(),code.getMsg(),null);
    }
}