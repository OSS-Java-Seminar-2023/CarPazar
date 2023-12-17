package hr.carpazar.controllers;

import hr.carpazar.Dtos.UserDto;
import hr.carpazar.models.User;
import hr.carpazar.services.HashService;
import hr.carpazar.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import org.springframework.ui.Model;

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
    public String openUserPage(Model model) {
        String usernameToFetch = "debagrlebili";
        Optional<User> userOptional = Optional.ofNullable(userService.fetchUserByUsername(usernameToFetch));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("FirstNameFromDatabase", user.getFullName().split(" ")[0]);
            model.addAttribute("LastNameFromDatabase", user.getFullName().split(" ")[1]);
            model.addAttribute("BirthDateFromDatabase", user.getBirthDate());
            model.addAttribute("PhoneNumFromDatabase", user.getPhoneNumber());
            model.addAttribute("usernameFromDatabase", user.getUserName());
            model.addAttribute("emailFromDatabase", user.getEmail());
        } else {
            //error?
            System.out.println("A sta si ovo napravija jeben te pijana");
        }

        return "user";
    };

    @PostMapping(path="/register")
    public String registrationCheck(@ModelAttribute UserDto userDto){
        User user = UserService.createUserFromDto(userDto);

        if (userService.isExistingCheck(user))
            System.out.println("handleanje postojeceg korisnika");  //  <--- handleat existing info
        else
            userService.registerUser(user);

        return "home";
    }

    @PostMapping(path="/login")
    public String loginValidation(@RequestParam String username, @RequestParam String password){
        Boolean temp = false;
        String dbHashedPassword = userService.preparePasswordComparing(username, password);
        if (HashService.comparePasswords(password, dbHashedPassword))
            temp = true;
        else                                                        //  <--- handleat successful login I ONEMOGUCIT "@" PRI REGISTRACIJI u username fieldu
            temp = false;
        System.out.println("Password match: " + temp);
        return "home";
    }
}