package hr.carpazar.controllers;

import hr.carpazar.models.User;
import hr.carpazar.services.HashService;
import hr.carpazar.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Hashtable;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService = new UserService();

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

    @GetMapping(path="/user")
    public String openUserPage() {return "user";};

    @PostMapping("/login")
    public String registrationCheck(
        @RequestParam String firstName,
        @RequestParam String surname,
        @RequestParam String birthDate,
        @RequestParam(required = false) Integer phoneNumber,
        @RequestParam String email,
        @RequestParam String username,
        @RequestParam String password
    ){
        Map<String, String> postParams = new Hashtable<>();
        postParams.put("firstName", firstName);
        postParams.put("surname", surname);
        postParams.put("birthDate", birthDate);
        if(phoneNumber != null)
            postParams.put("phoneNumber", phoneNumber.toString());
        else
            postParams.put("phoneNumber", "");
        postParams.put("email", email);
        postParams.put("username", username);
        postParams.put("password", HashService.generateSHA512(password));

        User user = UserService.createUser(postParams);

        if (userService.isExistingCheck(user))
            System.out.println("handleanje postojeceg korisnika");  //  <--- handleat existing info
        else
            userService.registerUser(user);

        return "home";
    }

    @PostMapping(path="/home")
    public String loginValidation(@RequestParam String username, @RequestParam String password){
        Boolean temp = false;
        String dbHashedPassword = userService.preparePasswordComparing(username, password);
        if (HashService.comparePasswords(password, dbHashedPassword))
            temp = true;
        else                                                                    //      <---   handleat successful login I ONEMOGUCIT "@" PRI REGISTRACIJI u username fieldu
            temp = false;
        System.out.println("Password match: " + temp);
        return "home";
    }
}