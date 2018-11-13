package com.cloudnative03.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private ConfigurationProjectProperties pro;

    @GetMapping("/pro")
    public String testMethod(){
        return pro.getProjectName();
    }

    @GetMapping("/hello")
    public String helloMethod(){
        return "hello";
    }
}
