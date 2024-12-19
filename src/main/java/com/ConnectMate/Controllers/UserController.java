package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Entities.User;
import com.ConnectMate.Forms.QueryForm;
import com.ConnectMate.Helpers.Helper;
import com.ConnectMate.Helpers.Message;
import com.ConnectMate.Helpers.MessageType;
import com.ConnectMate.Services.EmailService;
import com.ConnectMate.Services.ImageService;
import com.ConnectMate.Services.QueryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ConnectMate.Services.UserService;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private QueryService userQueryService;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }
    @RequestMapping(value = "/profile")
    public String userProfile() {
        return "user/profile";
    }
    @RequestMapping(value = "/403")
    public String user403() {
        return "user/403";
    }

    @RequestMapping(value = "/contactUs")
    public String userContactUs(Model model) {
        QueryForm queryForm = new QueryForm();
        model.addAttribute("queryForm", queryForm);
        return "user/contactUs";
    }

    @RequestMapping(value = "/processQuery", method = RequestMethod.POST)
    public String processQuery(
            @Valid @ModelAttribute QueryForm queryForm,
            BindingResult result,
            HttpSession session,
            Authentication authentication) {

        logger.info("Processing Query");

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.error(error.toString()));
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build()
            );
            return "user/contactUs";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Query query = new Query();
        String queryId = UUID.randomUUID().toString();
        query.setId(queryId);
        query.setName(queryForm.getName());
        query.setTitle(queryForm.getTitle());
        query.setContent(queryForm.getDescription());
        query.setDate(new Date());
        String fileUrl = "";
        if (queryForm.getImage() != null && !queryForm.getImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            fileUrl = imageService.uploadImage(queryForm.getImage(), filename);
            query.setImage(fileUrl);
        }
        query.setResolved(false);
        query.setUser(user);

        // Saving query to the database
        query = userQueryService.save(query);

        emailService.sendQuery(username, queryForm.getName(), queryId, queryForm.getTitle(),queryForm.getDescription(), fileUrl);
        logger.info("Query: " + query.toString());

        return "redirect:/user/contactUs";
    }

}