package com.example.violationmanagementsystem.service;

import com.example.violationmanagementsystem.dto.request.LoginRequest;
import com.example.violationmanagementsystem.dto.response.LoginResponse;
import com.example.violationmanagementsystem.dto.response.UserResponse;
import com.example.violationmanagementsystem.entity.User;
import com.example.violationmanagementsystem.repo.UserRepo;
import com.example.violationmanagementsystem.security.AppUserDetails;
import com.example.violationmanagementsystem.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.username(), request.password()));

        AppUserDetails details = (AppUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(details.getId());

        return new LoginResponse(token, "Bearer", jwtService.getExpirationSeconds());
    }

    public String verify(User user) {
        Authentication authentication =
                authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken.unauthenticated(
                                user.getUsername(), user.getPassword()
                        ));

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        return jwtService.generateToken(appUserDetails.getId());
    }

    public List<User> getAllUsers() {
        return this.userRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).map(UserResponse::from);
    }
}
