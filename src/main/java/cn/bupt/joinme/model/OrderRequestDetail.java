package cn.bupt.joinme.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OrderRequestDetail implements Serializable {
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    private String requestId;
    private String userId;
    private Set<String> acceptUsers;
    private Date finishDate;
    private int price;
}
