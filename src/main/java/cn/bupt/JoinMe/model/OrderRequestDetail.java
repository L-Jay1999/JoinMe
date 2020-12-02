package cn.bupt.JoinMe.model;

import java.util.Date;
import java.util.Set;

public class OrderRequestDetail {
    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public Set<String> getAcceptUsers() {
        return AcceptUsers;
    }

    public void setAcceptUsers(Set<String> acceptUsers) {
        AcceptUsers = acceptUsers;
    }

    public Date getFinishDate() {
        return FinishDate;
    }

    public void setFinishDate(Date finishDate) {
        FinishDate = finishDate;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String RequestId;
    public String UserId;
    public Set<String> AcceptUsers;
    public Date FinishDate;
    public int Price;
}
