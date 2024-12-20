package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Entities.User;
import com.ConnectMate.Forms.EmailForm;
import com.ConnectMate.Forms.QuerySearchForm;
import com.ConnectMate.Forms.UserSearchForm;
import com.ConnectMate.Helpers.AppConstants;
import com.ConnectMate.Helpers.Message;
import com.ConnectMate.Helpers.MessageType;
import com.ConnectMate.Repositories.UserRepo;
import com.ConnectMate.Services.EmailService;
import com.ConnectMate.Services.QueryService;
import com.ConnectMate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private QueryService queryService;

    @RequestMapping(value = "/dashboard")
    public String adminDashboard(Model model) {
        System.out.println("Admin Dashboard");
        int usersCount = userService.getUsers().size();
        int enabledUsers = userService.enabledUser(true).size();
        int nonEnabledUsers = userService.enabledUser(false).size();
        int verifiedUser = userService.verifiedEmail(true).size();
        int nonVerifiedUser = userService.verifiedEmail(false).size();
        int resolvedQueries = queryService.queryResolvedOrNot(true).size();
        int notResolvedQueries = queryService.queryResolvedOrNot(false).size();

        List<Query> queries = queryService.findAll();

        model.addAttribute("usersCount", usersCount);
        model.addAttribute("enabledUsers", enabledUsers);
        model.addAttribute("verifiedUser", verifiedUser);
        model.addAttribute("nonEnabledUsers", nonEnabledUsers);
        model.addAttribute("resolvedQueries", resolvedQueries);
        model.addAttribute("nonVerifiedUser", nonVerifiedUser);
        model.addAttribute("notResolvedQueries", notResolvedQueries);

        model.addAttribute("queries", queries);
        model.addAttribute("querySearchForm", new QuerySearchForm());
        // Log the gathered statistics
        logger.info("Admin Dashboard Stats: Total Users = {}, Verified Users = {}, Non-Verified Users = {}, Enabled Users = {}, Non-Enabled Users = {}, Resolved Queries = {}, Not Resolved Queries = {}",
                usersCount, verifiedUser, nonVerifiedUser, enabledUsers, nonEnabledUsers, resolvedQueries, notResolvedQueries);
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

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public String getUsers(Model model,
                           @RequestParam(value = "page", defaultValue = "0") int page,
                           @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
                           @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                           @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        Page<User> users = userService.getAllUsers(size, page, sortBy, direction);
        if (users == null || users.isEmpty()) {
            users = Page.empty(); // Ensure an empty page object is returned when no users exist
        }
        model.addAttribute("pageContact", users);
        model.addAttribute("pageSize", size);
        model.addAttribute("userSearchForm", new UserSearchForm());
        return "admin/getUsers";
    }

//    Search Handler
    @RequestMapping(value = "/user/search")
    public String searchUsers(
            @ModelAttribute UserSearchForm userSearchForm,
            @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Model model
    ){
        Page<User> pageContact = null;
        if (userSearchForm.getField().equalsIgnoreCase("name")){
            pageContact = userService.searchByName(userSearchForm.getValue(), size, page, sortBy, direction);
        } else if (userSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = userService.searchByEmail(userSearchForm.getValue(), size, page, sortBy, direction);
        } else if (userSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = userService.searchByPhone(userSearchForm.getValue(), size, page, sortBy, direction);
        }
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("userSearchForm", userSearchForm);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "admin/adminSearch";
    }

    @RequestMapping(value="/sendEmail")
    public String sendEmail(Model model) {
        model.addAttribute("emailForm", new EmailForm());
        return "admin/sendEmail";
    }

    @RequestMapping(value = "sendMail", method = RequestMethod.POST)
    public String sendMail(@Valid @ModelAttribute EmailForm emailForm, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "admin/sendEmail";
        }
        List<User> userList;
        if (emailForm.getTo().isEmpty()) {
            userList = userService.getUsers();
            userList.remove("admin@gmail.com");
            if (userList.isEmpty()) {
                logger.warn("No users found to send the email.");
                Message message = Message
                        .builder()
                        .content("No users found to send the email.")
                        .type(MessageType.red)
                        .build();
                model.addAttribute("message", message);
            } else {
                String[] to = userList.stream().map(User::getEmail).toArray(String[]::new);
                try {
                    emailService.sendEmailWithHTML(to, emailForm.getSubject(), emailForm.getMytextarea());
                    logger.info("Email sent to: " + Arrays.toString(to));
                    Message message = Message
                            .builder()
                            .content("Email sent successfully")
                            .type(MessageType.green)
                            .build();
                    model.addAttribute("message", message);
                } catch (Exception e) {
                    Message message = Message
                            .builder()
                            .content("Error occurred: " + e.getMessage())
                            .type(MessageType.red)
                            .build();
                    model.addAttribute("message", message);
                }
            }
        } else {
            try {
                emailService.sendEmail(emailForm.getTo(), emailForm.getSubject(), emailForm.getMytextarea());
                logger.info("Email sent to: " + emailForm.getTo() + ", Subject: " + emailForm.getSubject());
                Message message = Message
                        .builder()
                        .content("Email sent successfully")
                        .type(MessageType.green)
                        .build();
                model.addAttribute("message", message);
            } catch (Exception e) {
                Message message = Message
                        .builder()
                        .content("Error occurred: " + e.getMessage())
                        .type(MessageType.red)
                        .build();
                model.addAttribute("message", message);
            }
        }
        return "redirect:/admin/sendEmail";
    }
    @RequestMapping(value = "/view")
    public String view(Model model) {
        return "admin/queryDescription";
    }

    @GetMapping("/queries")
    public String getQueries(@RequestParam(required = false) String status, Model model) {
        List<Query> queries;
        if ("resolved".equalsIgnoreCase(status)) {
            queries = queryService.queryResolvedOrNot(true);
        } else if ("not-resolved".equalsIgnoreCase(status)) {
            queries = queryService.queryResolvedOrNot(false);
        } else {
            queries = queryService.findAll();
        }
        model.addAttribute("queries", queries);
        return "admin/queryDescription";
    }
}