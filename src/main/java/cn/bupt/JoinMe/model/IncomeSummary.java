package cn.bupt.JoinMe.model;

import cn.bupt.JoinMe.share.OrderType;

import java.util.Date;

public class IncomeSummary {
    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getLocale() {
        return Locale;
    }

    public void setLocale(String locale) {
        Locale = locale;
    }

    public cn.bupt.JoinMe.share.OrderType getOrderType() {
        return OrderType;
    }

    public void setOrderType(cn.bupt.JoinMe.share.OrderType orderType) {
        OrderType = orderType;
    }

    public int getCountOfSuccess() {
        return CountOfSuccess;
    }

    public void setCountOfSuccess(int countOfSuccess) {
        CountOfSuccess = countOfSuccess;
    }

    public int getIncome() {
        return Income;
    }

    public void setIncome(int income) {
        Income = income;
    }

    public Date Date;
    public String Locale;
    public OrderType OrderType;
    public int CountOfSuccess;
    public int Income;
}
