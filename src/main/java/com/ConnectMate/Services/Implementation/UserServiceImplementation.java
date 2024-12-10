package com.ConnectMate.Services.Implementation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ConnectMate.Entities.User;
import com.ConnectMate.Helpers.AppConstants;
import com.ConnectMate.Helpers.Helper;
import com.ConnectMate.Helpers.ResourceNotFoundException;
import com.ConnectMate.Repositories.UserRepo;
import com.ConnectMate.Services.EmailService;
import com.ConnectMate.Services.UserService;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private  Helper helper;

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepo.save(user);
        String emailLink = helper.getLinkForEmailVerificatiton(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        User user2 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

//    @Override
//    public boolean isUserEnabled(String userId) {
//        if (isUserExist(userId)) {
//            User user2 = userRepo.findById(userId).orElse(null);
//            return user2 != null && user2.isEnabled();
//        }
//        return false;
//    }

    @Override
    public Optional<User> isEnabled(String email) {
        User user2 = getUserByEmail(email);
        if (user2 != null) {
            user2.setEnabled(!user2.isEnabled());
            return Optional.of(userRepo.save(user2));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> isEmailVerified(String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.setEmailVerified(!user.isEmailVerified());
            return Optional.of(userRepo.save(user));
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> isPhoneVerified(String email) {
        User user = getUserByEmail(email);
        if (user != null) {
            user.setPhoneVerified(!user.isPhoneVerified());
            return Optional.of(userRepo.save(user));
        }
        return Optional.empty();
    }

    @Override
    public Page<User> searchByName(String nameKeyword, int size, int page, String sortBy, String direction) {
         Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return userRepo.findByNameContaining(nameKeyword,pageable);
    }

    @Override
    public Page<User> searchByEmail(String emailKeyword, int size, int page, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return userRepo.findByEmailContaining(emailKeyword,pageable);
    }

    @Override
    public Page<User> searchByPhone(String phoneKeyword, int size, int page, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return userRepo.findByPhoneNumberContaining(phoneKeyword,pageable);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<User> verifiedEmail(boolean isEmailVerified) {
        return userRepo.findAllByEmailVerified(isEmailVerified);
    }

    @Override
    public List<User> enabledUser(boolean isEnabled) {
        return userRepo.findAllByEnabled(isEnabled);
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public Page<User> getAllUsers(int size,int page,String sortBy,String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return userRepo.findAll(pageable);
    }


    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}