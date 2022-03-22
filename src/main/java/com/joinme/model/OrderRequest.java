package com.joinme.model;

import com.joinme.share.Count;
import com.joinme.share.RequestState;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class OrderRequest implements Serializable {

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId() {
        this.requestId = Count.orderRequestCount.getAndIncrement();
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

    @Id
    private String id;
    private Integer requestId;
    private Integer orderId;
    private Integer userId;
    private String description;
    private Date createDate;
    private Date modifyDate;
    private RequestState requestState;
}
