package cn.bupt.joinme.model;

import cn.bupt.joinme.share.OrderType;

import java.io.Serializable;
import java.util.Date;

public class SearchRequest implements Serializable {

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private Date startDate;
    private Date endDate;
    private OrderType orderType;
    private String location;
}
