package com.ConnectMate.Services.Implementation;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Repositories.QueryRepo;
import com.ConnectMate.Services.QueryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Query> findAll() {
        return queryRepo.findAll();
    }

    @Override
    public List<Query> queryResolvedOrNot(boolean isResolved) {
        return queryRepo.findByIsResolved(isResolved);
    }

    @Override
    public Query findById(String id) {
        return queryRepo.findById(id);
    }

    @Override
    @Transactional
    public void delete(String id) {
        queryRepo.deleteById(id);
    }
}
