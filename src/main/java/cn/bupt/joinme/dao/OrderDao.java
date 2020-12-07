package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.Order;
import cn.bupt.joinme.model.OrderRequest;
import cn.bupt.joinme.model.User;
import cn.bupt.joinme.share.OrderState;
import cn.bupt.joinme.share.RequestState;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        List<Order> orders = mongoTemplate.findAll(Order.class);
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

    public boolean deleteOneOrder(User user, Integer id) {
        Query query = new Query(Criteria.where("orderId").is(id));
        Order order = mongoTemplate.findOne(query, Order.class);
        if (order == null || order.getOrderState() != OrderState.Initial)
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

    public boolean updateOneOrder(User user, Integer id, Order newOrder) {
        Query query = new Query(Criteria.where("orderId").is(id));
        Order order = mongoTemplate.findOne(query, Order.class);
        if (order == null || order.getOrderState() != OrderState.Initial)
            return true;

        if (!user.getUserId().equals(order.getUserId()))
            return false;
        else {
            order.setDescription(newOrder.getDescription());
            order.setModifyDate(new Date());
            order.setOrderName(newOrder.getOrderName());
            order.setOrderType(newOrder.getOrderType());
            order.setPicture(newOrder.getPicture());
            order.setNumber(newOrder.getNumber());
            order.setEndDate(newOrder.getEndDate());
            mongoTemplate.save(order);
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

    public boolean acceptOrder(User user, Integer orderId, OrderRequest orderRequest) {
        Query query = new Query(Criteria.where("orderId").is(orderId));
        Order res = mongoTemplate.findOne(query, Order.class);
        if (res == null || res.getUserId().equals(user.getUserId()))
            return false;
        else if (res.getEndDate().getTime() <= new Date().getTime() ||
                (res.getOrderState() != OrderState.Initial && res.getOrderState() != OrderState.Respond))
            return false;
        else {
            query = new Query(Criteria.where("orderId").is(orderId).and("requestState").is(RequestState.Accept));
            long count = mongoTemplate.count(query, OrderRequest.class);
            if (count >= res.getNumber())
                return false;
            res.setOrderState(OrderState.Respond);
            mongoTemplate.save(res);
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
