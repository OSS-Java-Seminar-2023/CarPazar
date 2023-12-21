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
        String usernameToFetch = "novi123";
        Optional<User> userOptional = Optional.ofNullable(userService.fetchUserByUsername(usernameToFetch));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDTO = new UserDto();

            String[] names = user.getFullName().split(" ");
            userDTO.setFirstName(names[0]);
            userDTO.setSurname(names[1]);
            userDTO.setBirthDate(String.valueOf(user.getBirthDate()));
            userDTO.setPhoneNumber(Integer.valueOf(user.getPhoneNumber()));
            userDTO.setUsername(user.getUserName());
            userDTO.setEmail(user.getEmail());

            model.addAttribute("userDTO", userDTO);
        } else {
            System.out.println("User not found");
        }

        return "user";
    }

    @PostMapping(path = "/login")
    public String loginValidation(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            String dbHashedPassword = userService.preparePasswordComparing(username, password);

            if (dbHashedPassword == null) {
                System.out.println("User doesn't exist");
                model.addAttribute("alert", "User does not exist!");
                return "login";
            }

            if (HashService.comparePasswords(password, dbHashedPassword)) {
                return "home";
            } else {
                model.addAttribute("alert", "Wrong password! Try again!");
                return "login";
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            model.addAttribute("alert", "User doesn't exist!");
            return "login";
        }
    }

    @PostMapping(path="/register")
    public String registrationCheck(@ModelAttribute UserDto userDto){
        User user = UserService.createUserFromDto(userDto);

        if (userService.isExistingCheck(user))
            System.out.println("handleanje postojeceg korisnika");  //  <--- handleat existing info
        else
            userService.registerUser(user);

        return "home";
    }
}
