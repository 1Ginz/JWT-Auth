package com.example.resource.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

    @GetMapping("/admin")
    public ResponseEntity<String> AdminAuthor(){
        return ResponseEntity.ok("Admin success");
    }

    @GetMapping("/anonymous")
    public ResponseEntity<String> NoAuth(){
        return ResponseEntity.ok("Anonymous success");
    }
}
