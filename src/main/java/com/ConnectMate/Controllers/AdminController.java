package com.ConnectMate.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/dashboard")
    public String adminDashboard() {
        return "admin/adminDashboard";
    }


}
