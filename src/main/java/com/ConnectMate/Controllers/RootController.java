package com.ConnectMate.Controllers;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ConnectMate.Entities.User;
import com.ConnectMate.Helpers.Helper;
import com.ConnectMate.Services.UserService;

@ControllerAdvice
public class RootController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);
        User user = userService.getUserByEmail(username);

        if (user == null) {
            logger.error("User not found with email: {}", username);
            return; // or handle the error gracefully
        }

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        logger.info("User logged in ROLE: {}", user.getRoleList().toString());

        if (user.isEnabled()) {
            model.addAttribute("loggedInUser", user);
        }
    }
}
