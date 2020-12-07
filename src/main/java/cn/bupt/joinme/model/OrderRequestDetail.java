package cn.bupt.joinme.model;

import cn.bupt.joinme.share.Count;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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

    public List<Integer> getAcceptUsers() {
        return acceptUsers;
    }

    public void setAcceptUsers(List<Integer> acceptUsers) {
        this.acceptUsers = acceptUsers;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Id
    private Integer detailId;
    private Integer orderId;
    private Integer userId;
    private List<Integer> acceptUsers;
    private Date finishDate;
}
