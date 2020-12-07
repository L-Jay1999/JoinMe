package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.*;
import cn.bupt.joinme.share.OrderState;
import cn.bupt.joinme.share.RequestState;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Component
public class OrderRequestDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<OrderRequest> getAllRequest() {
        return mongoTemplate.findAll(OrderRequest.class);
    }

    public List<OrderRequest> getOrderRequest(User user) {
        Query query = new Query(Criteria.where("userId").is(user.getUserId()));
        return mongoTemplate.find(query, OrderRequest.class);
    }

    public List<OrderRequest> getOrderRequestBasedOnOrderId(User user, Integer id) {
        Query query = new Query(Criteria.where("orderId").is(id));
        Order order = mongoTemplate.findOne(query, Order.class);
        if (order == null || !order.getUserId().equals(user.getUserId()))
            return null;
        query = new Query(Criteria.where("orderId").is(id));
        return mongoTemplate.find(query, OrderRequest.class);
    }

    public OrderRequest getOneOrderRequest(User user, Integer id) {
        Query query = new Query(Criteria.where("requestId").is(id));
        OrderRequest res = mongoTemplate.findOne(query, OrderRequest.class);
        if (res == null || !res.getUserId().equals(user.getUserId()))
            return null;
        else
            return res;
    }

    public OrderRequest getOneOrderRequest(Integer id) {
        Query query = new Query(Criteria.where("requestId").is(id));
        return mongoTemplate.findOne(query, OrderRequest.class);
    }

    public boolean updateOrderRequest(User user, Integer id, OrderRequest orderRequest) {
        Query query = new Query(Criteria.where("requestId").is(id));
        OrderRequest orderRequest1 = mongoTemplate.findOne(query, OrderRequest.class);
        if (orderRequest1 == null || orderRequest1.getRequestState() != RequestState.UnReady)
            return true;
        if (!user.getUserId().equals(orderRequest1.getUserId()))
            return false;
        else {
            orderRequest1.setDescription(orderRequest.getDescription());
            orderRequest1.setModifyDate(new Date());
            mongoTemplate.save(orderRequest1);
            return true;
        }
    }

    public boolean deleteOrderRequest(User user, Integer id) {
        Query query = new Query(Criteria.where("requestId").is(id));
        OrderRequest orderRequest1 = mongoTemplate.findOne(query, OrderRequest.class);
        if (orderRequest1 == null || orderRequest1.getRequestState() != RequestState.UnReady)
            return true;
        if (!user.getUserId().equals(orderRequest1.getUserId()))
            return false;
        else {
            orderRequest1.setRequestState(RequestState.Decline);
            orderRequest1.setModifyDate(new Date());
            mongoTemplate.save(orderRequest1);
            return true;
        }
    }

    public boolean approveOrderRequest(User user, Integer id) {
        Query query = new Query(Criteria.where("requestId").is(id));
        OrderRequest orderRequest = mongoTemplate.findOne(query, OrderRequest.class);
        if (orderRequest == null || orderRequest.getRequestState() != RequestState.UnReady)
            return true;

        query = new Query(Criteria.where("orderId").is(orderRequest.getOrderId()));
        Order order = mongoTemplate.findOne(query, Order.class);
        if (order == null || !order.getUserId().equals(user.getUserId()))
            return false;
        else {
            orderRequest.setRequestState(RequestState.Accept);
            mongoTemplate.save(orderRequest);
            query = new Query(Criteria.where("orderId").is(order.getOrderId()).and("requestState").is(RequestState.Accept));
            long count = mongoTemplate.count(query, OrderRequest.class);
            if (count + 1 == order.getNumber()) {
                order.setOrderState(OrderState.Finish);
                mongoTemplate.save(order);
                OrderRequestDetail orderRequestDetail = new OrderRequestDetail();
                orderRequestDetail.setDetailId();
                orderRequestDetail.setOrderId(order.getOrderId());
                orderRequestDetail.setFinishDate(new Date());
                List<OrderRequest> or = mongoTemplate.find(query, OrderRequest.class);
                Set<Integer> res = new HashSet<>();
                for (OrderRequest o: or) {
                    res.add(o.getUserId());
                    query = new Query(Criteria.where("userId").is(o.getUserId()));
                    User temp = mongoTemplate.findOne(query, User.class);
                    IncomeSummary incomeSummary = new IncomeSummary();
                    incomeSummary.setLocale(temp.getCity());
                    incomeSummary.setIncome(1);
                    incomeSummary.setDate(new Date());
                    incomeSummary.setOrderType(order.getOrderType());
                    mongoTemplate.save(incomeSummary);
                }
                orderRequestDetail.setAcceptUsers(res);
                mongoTemplate.save(orderRequestDetail);

                IncomeSummary incomeSummary = new IncomeSummary();
                incomeSummary.setOrderType(order.getOrderType());
                incomeSummary.setLocale(user.getCity());
                incomeSummary.setIncome(3 * order.getNumber());
                incomeSummary.setDate(new Date());
                mongoTemplate.save(incomeSummary);
            }
            return true;
        }
    }

    public boolean denyOrderRequest(User user, Integer id) {
        Query query = new Query(Criteria.where("requestId").is(id));
        OrderRequest orderRequest = mongoTemplate.findOne(query, OrderRequest.class);
        if (orderRequest == null || orderRequest.getRequestState() != RequestState.UnReady)
            return true;

        query = new Query(Criteria.where("orderId").is(orderRequest.getOrderId()));
        Order order = mongoTemplate.findOne(query, Order.class);
        if (order == null || !order.getUserId().equals(user.getUserId()))
            return false;
        else {
            mongoTemplate.save(order);
            orderRequest.setRequestState(RequestState.Refuse);
            mongoTemplate.save(orderRequest);
            return true;
        }
    }
}