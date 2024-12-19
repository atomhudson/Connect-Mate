package com.ConnectMate.Services.Implementation;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Repositories.QueryRepo;
import com.ConnectMate.Services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryServiceImplementation implements QueryService {
    private final QueryRepo queryRepo;

    @Autowired
    public QueryServiceImplementation(QueryRepo queryRepo) {
        this.queryRepo = queryRepo;
    }
    @Override
    public Query save(Query query) {
        return queryRepo.save(query);
    }
}
