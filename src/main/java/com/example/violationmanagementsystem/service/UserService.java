package com.example.violationmanagementsystem.service;

import com.example.violationmanagementsystem.entity.User;
import com.example.violationmanagementsystem.repo.UserRepo;
import com.example.violationmanagementsystem.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken.unauthenticated(
                                user.getUsername(), user.getPassword()
                        ));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());

        return "fail";
    }

    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }
}
