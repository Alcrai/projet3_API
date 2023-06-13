package com.chattop.api.controller;

import com.chattop.api.entity.User;
import com.chattop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/user/{id}")
    public User getUserById(@PathVariable("id")int id){
        return userService.findById(id);
    }
}
