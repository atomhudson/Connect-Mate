package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Forms.ContactForm;
import com.ConnectMate.Forms.QueryForm;
import com.ConnectMate.Helpers.Helper;
import com.ConnectMate.Services.EmailService;
import com.ConnectMate.Services.ImageService;
import com.ConnectMate.Services.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ConnectMate.Entities.User;
import com.ConnectMate.Forms.UserForm;
import com.ConnectMate.Helpers.Message;
import com.ConnectMate.Helpers.MessageType;
import com.ConnectMate.Services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Date;
import java.util.UUID;

@Controller
public class PageController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private QueryService userQueryService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        model.addAttribute("name", "Substring Technologies");
        return "home";
    }


    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }

    // contact page

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("queryForm",new QueryForm());
        return "contact";
    }

    // this is showing login page
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // registration page
    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);

        return "register";
    }

    // processing register

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {
        System.out.println("Processing registration");
        // fetch form data
        // UserForm
        System.out.println(userForm);
        // validate form data
        if (rBindingResult.hasErrors()) {
            return "register";
        }
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://gravatar.com/avatar/27205e5c51cb03f862138b22bcb5dc20f94a342e744ff6df1b8dc8af3c865109?f=pg");
        user.setDate(new Date());
        user.setLastUpdated(new Date());
        User savedUser = userService.saveUser(user);
        System.out.println("user saved :"+savedUser);
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);
        return "redirect:/register";
    }
    @RequestMapping(value = "/queryCreation",method = RequestMethod.POST)
    public String queryCreation(
            @Valid @ModelAttribute QueryForm queryForm,
            BindingResult result,
            HttpSession session
    ) {

        Helper helper = new Helper();
        logger.info("Processing Query");

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.error(error.toString()));
            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build()
            );
            return "contact";
        }
        if (helper.isGmailAddress(queryForm.getName())) {
            Query query = new Query();
            String queryId = UUID.randomUUID().toString();
            query.setId(queryId);
            query.setName(queryForm.getName());
            query.setTitle(queryForm.getTitle());
            query.setContent(queryForm.getDescription());
            query.setDate(new Date());
            query.setResolved(false);
            query = userQueryService.save(query);
            emailService.sendQuery(queryForm.getName(), queryForm.getName(), queryId, queryForm.getTitle(), queryForm.getDescription(), "");
            logger.info("Query: " + query.toString());
            session.setAttribute("message", Message.builder()
                    .content("Query Created SuccessFully with queryId: " + queryId)
                    .type(MessageType.green)
                    .build()
            );

            return "contact";
        }
        return "contact";
    }
}
