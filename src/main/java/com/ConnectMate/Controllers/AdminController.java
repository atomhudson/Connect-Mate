package com.ConnectMate.Controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


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

    @RequestMapping(value = "/getusers")
    public String getUsers(Model model) {
        return "admin/manageUser";
    }



}
