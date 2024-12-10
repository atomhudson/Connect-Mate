package com.ConnectMate.Repositories;

import java.util.List;
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

    List<User> findAll();
    List<User> findAllByEmailVerified(boolean emailVerified);
    List<User> findAllByEnabled(boolean isEnabled);


    Page<User> findAll(Pageable pageable);
    Page<User> findByNameContaining(String name,Pageable pageable);
    Page<User> findByEmailContaining(String email,Pageable pageable);
    Page<User> findByPhoneNumberContaining(String phoneNumber,Pageable peable);


    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmailToken(String id);

}