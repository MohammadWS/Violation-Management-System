package com.example.violationmanagementsystem.dto.response;

public record LoginResponse(String token, String tokenType, long expiresIn) {}
