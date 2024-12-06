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

    List<User> getAllUsers();

    User getUserByEmail(String email);

//    boolean isUserEnabled(String userId);

    Optional<User> isEnabled(String email);

    Optional<User> isEmailVerified(String email);

    Optional<User> isPhoneVerified(String phone);
}
