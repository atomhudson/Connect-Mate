package com.ConnectMate.Controllers;


import com.ConnectMate.Entities.Contact;
import com.ConnectMate.Entities.User;
import com.ConnectMate.Forms.ContactSearchForm;
import com.ConnectMate.Forms.UserSearchForm;
import com.ConnectMate.Helpers.AppConstants;
import com.ConnectMate.Helpers.Helper;
import com.ConnectMate.Repositories.UserRepo;
import com.ConnectMate.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/dashboard")
    public String adminDashboard() {
        System.out.println("Admin Dashboard");
        return "admin/adminDashboard";
    }

    @RequestMapping(value = "/profile")
    public String adminProfile(Model model) {
        System.out.println("Admin Profile");
        return "admin/adminProfile";
    }

    @RequestMapping(value = "/createUser")
    public String adminCreateUser(Model model) {
        System.out.println("Admin Create User");
        return "admin/createUser";
    }

    @RequestMapping(value = "/getUsers")
    public String getUsers(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("pageContact", users);
        return "admin/getUsers";
    }

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model) {

        System.out.println("view users runs");
        List<User> users = userService.getAllUsers();
        logger.info("users: " + users.get(0).getProvider());
        System.out.println("Users: " + users);

        model.addAttribute("pageContact", users);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "admin/getUsers";
    }



}
