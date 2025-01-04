package com.ConnectMate.Controllers;

import com.ConnectMate.Entities.Query;
import com.ConnectMate.Services.EmailService;
import com.ConnectMate.Services.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/query")
public class QueryController {
    Logger logger = LoggerFactory.getLogger(UpdateUserInformation.class);

    @Autowired
    private QueryService queryService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/queryResolved/{queryId}")
    public String queryResolved(@PathVariable String queryId){
        Query query = queryService.findById(queryId);
        query.setResolved(true);
        String to;
        if (query.getUser() != null){
            to = query.getUser().getEmail();
        }else{
            to = query.getName();
        }
        String creatorName = query.getName();
        String subject = query.getTitle();
        String body = query.getContent();
        emailService.queryResolved(to,creatorName,queryId,subject,body,query.getImage());
        logger.info("Query resolved with id " + query);
        queryService.save(query);
        return "redirect:/admin/dashboard";
    }
    @RequestMapping(value = "/queryDelete/{queryId}")
    public String queryDelete(@PathVariable String queryId){
        logger.info("Query deleted with id " + queryId);
        Query query = queryService.findById(queryId);
//        String to = query.getUser().getEmail();
//        String creatorName = query.getName();
//        String subject = query.getTitle();
//        String body = query.getContent();
//        emailService.queryDeleted(to,creatorName,queryId,subject,body,query.getImage());
        queryService.delete(queryId);
        return "redirect:/admin/dashboard";
    }

}
