package com.example.violationmanagementsystem.rest;

import com.example.violationmanagementsystem.entity.User;
import com.example.violationmanagementsystem.repo.UserRepo;
import com.example.violationmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControl {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        System.out.println(user);
        return userService.verify(user);
    }

}
