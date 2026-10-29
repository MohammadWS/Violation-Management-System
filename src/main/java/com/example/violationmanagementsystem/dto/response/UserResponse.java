package com.example.violationmanagementsystem.dto.response;

import com.example.violationmanagementsystem.entity.User;

public record UserResponse(Long id, String username, String email, String nationalId, String role) {
    public static UserResponse from(User u) {
        return new UserResponse(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getNationalId(),
                u.getRole() != null ? u.getRole().getCode() : null);
    }
}