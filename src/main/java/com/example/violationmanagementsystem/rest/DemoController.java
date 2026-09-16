package com.example.violationmanagementsystem.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World";
    }
}
