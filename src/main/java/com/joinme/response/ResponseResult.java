package com.joinme.response;

import com.joinme.share.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseResult implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public ResponseResult(ResponseType r) {
        this.code = r.getCode();
        this.msg = r.getMsg();
        this.data = null;
    }

    public void setData(Object data) {
        this.data = data;
    }
}