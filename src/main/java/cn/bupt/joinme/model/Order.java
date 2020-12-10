package cn.bupt.joinme.model;

import cn.bupt.joinme.share.Count;
import cn.bupt.joinme.share.OrderState;
import cn.bupt.joinme.share.OrderType;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId() {
        this.orderId = Count.orderCount.getAndIncrement();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Id
    private String id;
    private Integer orderId;
    private Integer userId;
    private OrderType orderType;
    private String orderName;
    private String description;
    private int number;
    private Date endDate;
    private String picture;
    private Date createDate;
    private Date modifyDate;
    private OrderState orderState;
}
