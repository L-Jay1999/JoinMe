package cn.bupt.joinme.dao;

import cn.bupt.joinme.model.IncomeSummary;
import cn.bupt.joinme.model.SearchRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class IncomeDao {

    @Resource
    private MongoTemplate mongoTemplate;
    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");


    public List<IncomeSummary> getIncome(SearchRequest searchRequest) {
        Query query = new Query();
        query.addCriteria(Criteria.where("date").lt(searchRequest.getEndDate()).gte(searchRequest.getStartDate()));
        if (searchRequest.getOrderType() != null)
            query.addCriteria(Criteria.where("orderType").is(searchRequest.getOrderType()));
        if (!searchRequest.getLocation().equals(""))
            query.addCriteria(Criteria.where("location").is(searchRequest.getLocation()));
        query.with(Sort.by(Sort.Order.asc("data")));
        List<IncomeSummary> summaries = mongoTemplate.find(query, IncomeSummary.class);
        List<IncomeSummary> result = new ArrayList<>();

        for (int i = 0; i < summaries.size(); ++i) {
            IncomeSummary temp = new IncomeSummary();
            temp.setDate(summaries.get(i).getDate());
            temp.setIncome(summaries.get(i).getIncome());
            temp.setLocale(searchRequest.getLocation());
            temp.setOrderType(searchRequest.getOrderType());
            while (i + 1 < summaries.size() &&
                    fmt.format(summaries.get(i).getDate()).equals(fmt.format(summaries.get(i+1).getDate()))) {
                i++;
                temp.setIncome(temp.getIncome()+summaries.get(i).getIncome());
            }
            result.add(temp);
        }

        return result;
    }
}
