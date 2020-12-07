package cn.bupt.joinme.model;

import cn.bupt.joinme.share.RequestState;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRequest implements Serializable {
    static AtomicInteger globalId = new AtomicInteger(0);

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = globalId.getAndIncrement(); ;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public RequestState getRequestState() {
        return requestState;
    }

    public void setRequestState(RequestState requestState) {
        this.requestState = requestState;
    }

    private Integer requestId;
    private Integer orderId;
    private Integer userId;
    private String description;
    private Date createDate;
    private Date modifyDate;
    private RequestState requestState;
}
