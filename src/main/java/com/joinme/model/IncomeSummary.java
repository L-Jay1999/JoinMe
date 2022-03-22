package com.joinme.model;

import com.joinme.share.OrderType;

import java.io.Serializable;
import java.util.Date;

public class IncomeSummary implements Serializable {
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    private Date date;
    private String locale;
    private OrderType orderType;
    private int income;
}
