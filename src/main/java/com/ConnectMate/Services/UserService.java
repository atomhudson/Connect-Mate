package com.ConnectMate.Services;


import java.util.List;
import java.util.Optional;

import com.ConnectMate.Entities.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    Page<User> getAllUsers(int size,int page,String sortBy,String direction);

    User getUserByEmail(String email);

//    boolean isUserEnabled(String userId);

    Optional<User> isEnabled(String email);

    Optional<User> isEmailVerified(String email);

    Optional<User> isPhoneVerified(String phone);

    Page<User> searchByName(String nameKeyword,int size, int page, String sortBy, String direction);
    Page<User> searchByEmail(String emailKeyword,int size, int page, String sortBy, String direction);
    Page<User> searchByPhone(String phoneKeyword,int size, int page, String sortBy, String direction);

    List<User> getUsers();
    List<User> verifiedEmail(boolean isEmailVerified);
    List<User> enabledUser(boolean isEnabled);
}