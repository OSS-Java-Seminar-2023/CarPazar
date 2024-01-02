package hr.carpazar.controllers;

import hr.carpazar.Dtos.UserDto;
import hr.carpazar.models.User;
import hr.carpazar.services.HashService;
import hr.carpazar.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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

    @GetMapping(path = {"/user", "/user/{username}"})
    public String openUserPage(@PathVariable(name = "username", required = false) Optional<String> usernameOptional, Model model, HttpSession session) {
        String loggedInUsername = (String) session.getAttribute("user_username");
        String userId = (String) session.getAttribute("user_id");
        if (userId == null){
            return "login";
        }

        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();

            User user = userService.findByUserName2(username);
            if (user == null) {
                return "notFound";
            }

            if (!username.equals(loggedInUsername)) {
                model.addAttribute("username", username);
                return "otherUser";
            }
        }

        model.addAttribute("userId", userId);
        model.addAttribute("username", loggedInUsername);

        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName2(loggedInUsername));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDTO = new UserDto();
            String[] names = user.getFullName().split(" ");
            userDTO.setFirstName(names[0]);
            userDTO.setSurname(names.length > 1 ? names[1] : "");
            userDTO.setBirthDate(String.valueOf(user.getBirthDate()));
            userDTO.setPhoneNumber(Integer.valueOf(user.getPhoneNumber()));
            userDTO.setUsername(user.getUserName());
            userDTO.setEmail(user.getEmail());

            model.addAttribute("userDTO", userDTO);
        } else {
            return "notFound";
        }

        return "user";
    }

    @GetMapping("notFound")
    public String openNotFound() {
        return "notFound";
    }

    @GetMapping("otherUser")
    public String openOtherUserPage() {
        return "otheruser";
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
    public String loginValidation(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        try {
            User user=userService.authenticateUser(username,password);
            HttpSession session = request.getSession(true);
            session.setAttribute("user_id",user.getId());
            session.setAttribute("user_username",user.getUserName());
            return "redirect:/home";

        } catch (RuntimeException e) {
            if (!Objects.equals(e.getMessage(), "Wrong password!")){
                model.addAttribute("alert", "User not found!");
                return "login";
            }
            else{
                model.addAttribute("alert", e.getMessage());
                return "login";
            }

        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
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
