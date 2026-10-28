package com.example.violationmanagementsystem.controller;

import com.example.violationmanagementsystem.dto.request.LoginRequest;
import com.example.violationmanagementsystem.dto.response.LoginResponse;
import com.example.violationmanagementsystem.dto.response.UserResponse;
import com.example.violationmanagementsystem.entity.User;
import com.example.violationmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControl {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest.username());
        return userService.login(loginRequest);
    }

//    TODO: change into admin
    @GetMapping("/users")
    @PreAuthorize("hasRole('SYSADMIN')")
    public Page<UserResponse> getUsers(@PageableDefault(size = 20) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

}
