package hr.carpazar.controllers;

import hr.carpazar.models.User;
import hr.carpazar.services.HashService;
import hr.carpazar.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import hr.carpazar.services.HashService;

import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping(path="/home")
    public String home(){
        return "home";
    }

    @GetMapping(path="/login")
    public String openLoginForm(){
        return "login";
    }

    @GetMapping(path="/register")
    public String openRegistrationForm(){
        return "register";
    }

    @PostMapping("/login")       // post sa registera salje tu, mozda bi trebalo slat na login (samo prominit path postmappinga i trazit ponovni login)
    public String isSuccess(
        @RequestParam String firstName,
        @RequestParam String surname,
        @RequestParam String birthDate,
        @RequestParam(required = false) Integer phoneNumber,
        @RequestParam String email,
        @RequestParam String username,
        @RequestParam String password
    ){
        Dictionary<String, String> postParams = new Hashtable<>();
        postParams.put("firstName", firstName);
        postParams.put("surname", surname);
        postParams.put("birthDate", birthDate);
        postParams.put("phoneNumber", phoneNumber.toString());
        postParams.put("email", email);
        postParams.put("username", username);
        postParams.put("password", HashService.generateSHA512(password));

        System.out.println(postParams.toString());  // umisto printanja insertat u db

        return "home";
    }

    //public UserController(UserService userService) {
       /* this.userService = userService;
    }*/
}