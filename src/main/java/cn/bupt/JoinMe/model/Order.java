package cn.bupt.JoinMe.model;

import cn.bupt.JoinMe.share.OrderState;
import cn.bupt.JoinMe.share.OrderType;

import java.util.Date;

public class Order {
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

    public cn.bupt.JoinMe.share.OrderType getOrderType() {
        return OrderType;
    }

    public void setOrderType(cn.bupt.JoinMe.share.OrderType orderType) {
        OrderType = orderType;
    }

    public String getOrderName() {
        return OrderName;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        EndDate = endDate;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
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

    public cn.bupt.JoinMe.share.OrderState getOrderState() {
        return OrderState;
    }

    public void setOrderState(cn.bupt.JoinMe.share.OrderState orderState) {
        OrderState = orderState;
    }

    public String OrderId;
    public String UserId;
    public OrderType OrderType;
    public String OrderName;
    public String Description;
    public int Number;
    public Date EndDate;
    public String Picture;
    public Date CreateDate;
    public Date ModifyDate;
    public OrderState OrderState;
}
