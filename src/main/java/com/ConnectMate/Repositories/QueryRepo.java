package com.ConnectMate.Repositories;

import com.ConnectMate.Entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepo extends JpaRepository<Query, Integer> {
    List<Query> findAllBy();
    List<Query> findByIsResolved(boolean isResolved);
    Query findById(String id);
    void deleteById(String id);
}
