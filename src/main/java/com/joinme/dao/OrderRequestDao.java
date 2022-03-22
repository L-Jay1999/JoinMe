package com.joinme.dao;

import com.joinme.share.OrderState;
import com.joinme.share.RequestState;
import com.joinme.model.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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

    public List<OrderRequest> getOrderRequestBasedOnOrderId(User user, Order order) {
        if (!order.getUserId().equals(user.getUserId()))
            return null;
        Query query = new Query(Criteria.where("orderId").is(order.getOrderId()));
        return mongoTemplate.find(query, OrderRequest.class);
    }

    public OrderRequest getOneOrderRequest(Integer id) {
        Query query = new Query(Criteria.where("requestId").is(id));
        return mongoTemplate.findOne(query, OrderRequest.class);
    }

    public boolean updateOrderRequest(User user, OrderRequest oldRequest, OrderRequest orderRequest) {
        if (oldRequest.getRequestState() != RequestState.UnReady)
            return true;
        if (!user.getUserId().equals(oldRequest.getUserId()))
            return false;
        else {
            oldRequest.setDescription(orderRequest.getDescription());
            oldRequest.setModifyDate(new Date());
            mongoTemplate.save(oldRequest);
            return true;
        }
    }

    public boolean deleteOrderRequest(User user, OrderRequest orderRequest) {
        if (orderRequest.getRequestState() != RequestState.UnReady)
            return true;
        if (!user.getUserId().equals(orderRequest.getUserId()))
            return false;
        else {
            orderRequest.setRequestState(RequestState.Decline);
            orderRequest.setModifyDate(new Date());
            mongoTemplate.save(orderRequest);
            return true;
        }
    }

    public boolean approveOrderRequest(User user, OrderRequest orderRequest) {
        if (orderRequest.getRequestState() != RequestState.UnReady)
            return true;

        Query query = new Query(Criteria.where("orderId").is(orderRequest.getOrderId()));
        Order order = mongoTemplate.findOne(query, Order.class);
        if (order == null || !order.getUserId().equals(user.getUserId()))
            return false;
        else {
            orderRequest.setRequestState(RequestState.Accept);
            mongoTemplate.save(orderRequest);
            query = new Query(Criteria.where("orderId").is(order.getOrderId()).and("requestState").is(RequestState.Accept));
            long count = mongoTemplate.count(query, OrderRequest.class);
            if (count == order.getNumber()) {
                order.setOrderState(OrderState.Finish);
                mongoTemplate.save(order);
                OrderRequestDetail orderRequestDetail = new OrderRequestDetail();
                orderRequestDetail.setDetailId();
                orderRequestDetail.setOrderId(order.getOrderId());
                orderRequestDetail.setFinishDate(new Date());
                List<OrderRequest> or = mongoTemplate.find(query, OrderRequest.class);
                Set<Integer> res = new HashSet<>();
                for (OrderRequest o : or) {
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
                orderRequestDetail.setUserId(order.getUserId());
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

    public boolean denyOrderRequest(User user, OrderRequest orderRequest) {
        if (orderRequest.getRequestState() != RequestState.UnReady)
            return true;

        Query query = new Query(Criteria.where("orderId").is(orderRequest.getOrderId()));
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
