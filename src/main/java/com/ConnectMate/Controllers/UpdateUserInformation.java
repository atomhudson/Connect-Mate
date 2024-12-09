package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.User;
import com.ConnectMate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("admin/userUpdate")
public class UpdateUserInformation {
    Logger logger = LoggerFactory.getLogger(UpdateUserInformation.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/isEnabled/{emailId}")
    public String isEnabled(@PathVariable("emailId") String emailId, HttpSession session) {
        System.out.println("Method isEnabled called with emailId: " + emailId);
//        logger.info(emailId + "isEnabled" + userService.isUserEnabled(emailId));
        userService.isEnabled(emailId);
//        logger.info(emailId + "isEnabled" + userService.isUserEnabled(emailId));
        return "redirect:/admin/getUsers";
    }

    @RequestMapping(value = "/isEmailVerified/{emailId}")
    public String isEmailVerified(@PathVariable("emailId") String emailId, HttpSession session) {
        System.out.println("Method isEmailVerified called with emailId: " + emailId);
        userService.isEmailVerified(emailId);
        return "redirect:/admin/getUsers";
    }

    @RequestMapping(value = "/isPhoneVerified/{emailId}")
    public String isPhoneVerified(@PathVariable("emailId") String emailId, HttpSession session) {
        System.out.println("Method isEmailVerified called with emailId: " + emailId);
        userService.isPhoneVerified(emailId);
        return "redirect:/admin/getUsers";
    }

    @RequestMapping(value = "/deleteUser/{emailId}")
    public String delete(@PathVariable("emailId") String emailId, HttpSession session) {
        User user = userService.getUserByEmail(emailId);
        logger.info("delete User called with emailId: " + user.toString());
        System.out.println("Method deleteUser called with emailId: " + emailId);
        session.setAttribute("deletedUser", true);
        session.setAttribute("emailId", emailId);
        userService.deleteUser(user.getUserId());
        return "redirect:/admin/getUsers";
    }
}