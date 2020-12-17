package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.OrderRequestDetail;
import cn.bupt.joinme.model.SearchRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderRequestDetailDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<OrderRequestDetail> getRequestDetail(SearchRequest searchRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("finishDate").gte(searchRequest.getStartDate()).lt(searchRequest.getEndDate()));
        // query.addCriteria(Criteria.where("finishDate").gte(searchRequest.getStartDate()));
        return mongoTemplate.find(query, OrderRequestDetail.class);
    }
}
