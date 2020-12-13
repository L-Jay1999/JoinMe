package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.Order;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.share.OrderState;
import cn.bupt.joinme.share.RequestState;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<Order> getOrder() {
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("endDate"), Sort.Order.desc("orderId")));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        for (Order o: orders) {
            if (o.getEndDate().getTime() <= new Date().getTime()) {
                o.setOrderState(OrderState.Due);
                mongoTemplate.save(o);
            }
        }
        return orders;
    }

    public Order getOneOrder(Integer id) {
        Query query = new Query(Criteria.where("orderId").is(id));
        return mongoTemplate.findOne(query, Order.class);
    }

    public boolean deleteOneOrder(User user, Order order) {
        if (order.getOrderState() != OrderState.Initial)
            return true;

        if (!user.getUserId().equals(order.getUserId()))
            return false;
        else {
            order.setOrderState(OrderState.Cancel);
            order.setModifyDate(new Date());
            mongoTemplate.save(order);
            return true;
        }
    }

    public void updateOneOrder(Order order) {
        mongoTemplate.save(order);
    }

    public boolean updateOneOrder(User user, Order oldOrder, Order newOrder) {
        if (oldOrder.getOrderState() != OrderState.Initial)
            return true;

        if (!user.getUserId().equals(oldOrder.getUserId()))
            return false;
        else {
            oldOrder.setDescription(newOrder.getDescription());
            oldOrder.setModifyDate(new Date());
            oldOrder.setOrderName(newOrder.getOrderName());
            oldOrder.setOrderType(newOrder.getOrderType());
            oldOrder.setPicture(newOrder.getPicture());
            oldOrder.setNumber(newOrder.getNumber());
            oldOrder.setEndDate(newOrder.getEndDate());
            mongoTemplate.save(oldOrder);
            return true;
        }
    }

    public void issueOrder(User user, Order order) {
        order.setCreateDate(new Date());
        order.setModifyDate(new Date());
        order.setOrderId();
        order.setUserId(user.getUserId());
        order.setOrderState(OrderState.Initial);
        mongoTemplate.save(order);
    }

    public boolean acceptOrder(User user, Order order, OrderRequest orderRequest) {
        if (order.getUserId().equals(user.getUserId()))
            return false;
        else if (order.getEndDate().getTime() <= new Date().getTime() ||
                (order.getOrderState() != OrderState.Initial && order.getOrderState() != OrderState.Respond))
            return false;
        else {
            Query query = new Query(Criteria.where("orderId").is(order.getOrderId()).and("requestState").is(RequestState.Accept));
            long count = mongoTemplate.count(query, OrderRequest.class);
            if (count >= order.getNumber())
                return false;
            order.setOrderState(OrderState.Respond);
            mongoTemplate.save(order);
            orderRequest.setOrderId(order.getOrderId());
            orderRequest.setUserId(user.getUserId());
            orderRequest.setRequestId();
            orderRequest.setCreateDate(new Date());
            orderRequest.setModifyDate(new Date());
            orderRequest.setRequestState(RequestState.UnReady);
            mongoTemplate.save(orderRequest);
            return true;
        }
    }

    public List<Order> getIssuedOrder(User user) {
        Query query = new Query(Criteria.where("userId").is(user.getUserId()));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        for (Order o: orders) {
            if (o.getEndDate().getTime() <= new Date().getTime()) {
                o.setOrderState(OrderState.Due);
                mongoTemplate.save(o);
            }
        }
        return orders;
    }

    public boolean checkAcceptOrder(User user, Integer id) {
        Query query = new Query(Criteria.where("userId").is(user.getUserId()));
        List<OrderRequest> requests= mongoTemplate.find(query, OrderRequest.class);
        for (OrderRequest or: requests)
            if (or.getOrderId().equals(id))
                return true;
        return false;
    }

    public List<Order> getRequestOrder(User user) {
        Query query = new Query(Criteria.where("userId").is(user.getUserId()));
        List<OrderRequest> requests = mongoTemplate.find(query, OrderRequest.class);
        List<Order> res = new ArrayList<>();
        for (OrderRequest or: requests) {
            query = new Query(Criteria.where("orderId").is(or.getOrderId()));
            res.add(mongoTemplate.findOne(query, Order.class));
        }
        return res;
    }
}
