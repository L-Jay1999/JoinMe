package cn.bupt.joinme.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRequestDetail implements Serializable {

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Set<String> getAcceptUsers() {
        return acceptUsers;
    }

    public void setAcceptUsers(Set<String> acceptUsers) {
        this.acceptUsers = acceptUsers;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private Integer requestId;
    private Integer userId;
    private Set<String> acceptUsers;
    private Date finishDate;
    private int price;
}
