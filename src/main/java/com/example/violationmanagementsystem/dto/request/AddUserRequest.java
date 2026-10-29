package com.example.violationmanagementsystem.dto.request;

public record AddUserRequest(String username, String password, String nationalId, String email, long roleId) {
}
