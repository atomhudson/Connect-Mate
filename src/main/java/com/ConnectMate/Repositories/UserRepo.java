package com.ConnectMate.Repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ConnectMate.Entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

//    Page<User> getAllUsers(Pageable pageable);
//    Optional<User> getAllUsers();

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmailToken(String id);

}