package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.IncomeSummary;
import cn.bupt.joinme.model.SearchRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class IncomeDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public List<IncomeSummary> getIncome(SearchRequest searchRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("date").lt(searchRequest.getEndDate()));
        query.addCriteria(Criteria.where("date").gte(searchRequest.getStartDate()));
        if (searchRequest.getOrderType() != null)
            query.addCriteria(Criteria.where("orderType").is(searchRequest.getOrderType()));
        if (searchRequest.getLocation() != null)
            query.addCriteria(Criteria.where("location").is(searchRequest.getLocation()));
        return mongoTemplate.find(query, IncomeSummary.class);
    }
}
