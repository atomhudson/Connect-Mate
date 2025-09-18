package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.User;
import com.ConnectMate.Forms.UserForm;
import com.ConnectMate.Helpers.AppConstants;
import com.ConnectMate.Helpers.Message;
import com.ConnectMate.Helpers.MessageType;
import com.ConnectMate.Repositories.UserRepo;
import com.ConnectMate.Services.ImageService;
import com.ConnectMate.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/createUser")
public class CreateUserController {
    private Logger logger = LoggerFactory.getLogger(CreateUserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserRepo userRepo;


    @RequestMapping(value = "/add")
    public String createUser(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "admin/createUser";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute UserForm userForm, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> logger.info(error.toString()));
            session.setAttribute("message", Message.builder()
                    .content("Please correct the errors in the form.")
                    .type(MessageType.red)
                    .build());
            return "admin/createUser";
        }

        String username = userForm.getEmail();

        Optional<User> existingUser = userRepo.findByEmail(username);
        if (existingUser.isPresent()) {
            session.setAttribute("message", Message.builder()
                    .content("User already exists in the system.")
                    .type(MessageType.red)
                    .build());
            return "admin/createUser";
        }
        // Create new user
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://gravatar.com/avatar/27205e5c51cb03f862138b22bcb5dc20f94a342e744ff6df1b8dc8af3c865109?f=pg");
        if ("ROLE_USER".equals(userForm.getUserType())) {
            user.setRoleList(List.of(AppConstants.ROLE_USER));
        } else {
            user.setRoleList(List.of(AppConstants.ROLE_ADMIN));
        }

        User savedUser = userService.saveUser(user);
        System.out.println("User created: "+savedUser);
        session.setAttribute("message", Message.builder()
                .content("User created successfully.")
                .type(MessageType.green)
                .build());

        return "redirect:/admin/createUser/add";
    }
}
