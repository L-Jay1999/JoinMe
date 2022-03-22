package com.joinme.share;


import com.joinme.model.Order;
import com.joinme.model.OrderRequest;
import com.joinme.model.OrderRequestDetail;
import com.joinme.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Count implements CommandLineRunner {

    @Resource
    private MongoTemplate mongoTemplate;

    public static AtomicInteger orderCount;
    public static AtomicInteger userCount;
    public static AtomicInteger orderRequestCount;
    public static AtomicInteger orderRequestDetailCount;

    @Override
    public void run(String... args) throws Exception {
        userCount = new AtomicInteger(mongoTemplate.findAll(User.class).size());
        orderCount = new AtomicInteger(mongoTemplate.findAll(Order.class).size());
        orderRequestCount = new AtomicInteger(mongoTemplate.findAll(OrderRequest.class).size());
        orderRequestDetailCount = new AtomicInteger(mongoTemplate.findAll(OrderRequestDetail.class).size());
    }
}