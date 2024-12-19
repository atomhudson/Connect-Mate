package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Entities.User;
import com.ConnectMate.Services.QueryService;
import com.ConnectMate.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ConnectMate.Entities.Contact;
import com.ConnectMate.Services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private QueryService queryService;
    @Autowired
    private UserService userService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId) {
        return contactService.getById(contactId);
    }

    @GetMapping("/queries/{queriesId}")
    public Query getQueries(@PathVariable String queriesId) {return queryService.findById(queriesId);}

    @GetMapping("/userData/{userId}")
    public User getUserData(@PathVariable String userId) {
        User user = userService.getUserByEmail(userId);
        return user;
    }
}
