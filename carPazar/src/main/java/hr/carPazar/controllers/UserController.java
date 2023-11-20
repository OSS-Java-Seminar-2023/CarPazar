package hr.carPazar.controllers;

import hr.carPazar.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path="/hello")
    public String hello(){
        return "hello world";
    }

    //public UserController(UserService userService) {
       /* this.userService = userService;
    }*/
}