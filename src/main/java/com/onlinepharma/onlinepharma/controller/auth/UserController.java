package com.onlinepharma.onlinepharma.controller.auth;


import com.onlinepharma.onlinepharma.criteria.auth.UserCriteria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/secured")
    public String secured(UserCriteria criteria) {
        return "Secured controller";
    }
}
