package com.example.student.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    @Value("${developer.name}")
    private String developerName;
    @Value("${developer.role}")
    private String developerRole;

    @GetMapping()
    public HashMap<String, String> getDeveloperDetail() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", developerName);
        map.put("role", developerRole);

        return map;
    }
}
