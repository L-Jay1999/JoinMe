package cn.bupt.joinme.exception;

import cn.bupt.joinme.share.ResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException{

    private ResponseType code;

    public BaseException(ResponseType code) {
        this.code = code;
    }

    public BaseException(Throwable cause, ResponseType code) {
        super(cause);
        this.code = code;
    }
}