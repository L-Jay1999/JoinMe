package cn.bupt.JoinMe.model;

import cn.bupt.JoinMe.share.RequestState;

import java.util.Date;

public class OrderRequest {
    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public Date getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        ModifyDate = modifyDate;
    }

    public cn.bupt.JoinMe.share.RequestState getRequestState() {
        return RequestState;
    }

    public void setRequestState(cn.bupt.JoinMe.share.RequestState requestState) {
        RequestState = requestState;
    }

    public String RequestId;
    public String OrderId;
    public String UserId;
    public String Description;
    public Date CreateDate;
    public Date ModifyDate;
    public RequestState RequestState;
}
