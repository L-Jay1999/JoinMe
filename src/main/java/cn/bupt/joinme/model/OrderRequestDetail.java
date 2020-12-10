package cn.bupt.joinme.model;

import cn.bupt.joinme.share.Count;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OrderRequestDetail implements Serializable {

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId() {
        this.detailId = Count.orderRequestDetailCount.getAndIncrement();
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

    public Set<Integer> getAcceptUsers() {
        return acceptUsers;
    }

    public void setAcceptUsers(Set<Integer> acceptUsers) {
        this.acceptUsers = acceptUsers;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Id
    private String id;
    private Integer detailId;
    private Integer orderId;
    private Integer userId;
    private Set<Integer> acceptUsers;
    private Date finishDate;
}
