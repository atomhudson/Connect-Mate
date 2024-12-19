package com.ConnectMate.Services;

import com.ConnectMate.Entities.Query;

import java.util.List;

public interface QueryService {
    Query save(Query query);
    List<Query> findAll();
    List<Query> queryResolvedOrNot(boolean isResolved);
    Query findById(String id);
}
