package hr.carPazar.controllers;

import hr.carPazar.repositories.UserService.java

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
}