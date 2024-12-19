package com.ConnectMate.Repositories;

import com.ConnectMate.Entities.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepo extends JpaRepository<Query, Integer> {

}
