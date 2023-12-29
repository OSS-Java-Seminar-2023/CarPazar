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

    @GetMapping(path="/user")
    public String openUserPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user_id");
        if (userId == null){
            return "login";
        }
        String username = (String) session.getAttribute("user_username");

        model.addAttribute("userId", userId);
        model.addAttribute("username", username);

        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));

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
    public String loginValidation(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        try {
            User user=userService.authenticateUser(username,password);
            session.setAttribute("user_id",user.getId());
            session.setAttribute("user_username",user.getUserName());
            return "home";

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
    public String logout(Model model,HttpSession session){
/*        String message=(String)session.getAttribute("user_username") + "logged out succesfully!";
        model.addAttribute("logged_out", message);*/
        session.invalidate();
        return "login";
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
