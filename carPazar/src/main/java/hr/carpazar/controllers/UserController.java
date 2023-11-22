package hr.carpazar.controllers;

import hr.carpazar.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/hello")
    public String hello(){
        return "hello world";
    }

    @GetMapping(path="/login")
    public String temp(){
        return "login placeholder";
    }

    //public UserController(UserService userService) {
       /* this.userService = userService;
    }*/
}