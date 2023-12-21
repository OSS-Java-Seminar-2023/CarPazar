package hr.carpazar.controllers;

import hr.carpazar.Dtos.UserDto;
import hr.carpazar.models.User;
import hr.carpazar.services.HashService;
import hr.carpazar.services.UserService;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(usernameToFetch));

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
            return "Not logged in";
        }

        return "user";
    }

    @PostMapping(path="/user/update")
    public String userUpdate(@ModelAttribute UserDto userDto,Model model) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User existingUser = userService.findByUserName(username);
        existingUser.setFullName(userDto.getFirstName()+" "+userDto.getSurname());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(userDto.getBirthDate());
        existingUser.setBirthDate(parsedDate);
        existingUser.setPhoneNumber(String.valueOf(userDto.getPhoneNumber()));
        existingUser.setEmail(userDto.getEmail());
        existingUser.setUserName(userDto.getUsername());

        userService.saveUser(existingUser);
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
