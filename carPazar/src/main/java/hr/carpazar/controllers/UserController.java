package hr.carpazar.controllers;

import hr.carpazar.dtos.*;
import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.repositories.UserRepository;
import hr.carpazar.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ListingService listingService;
    @Autowired
    private ChatService chatService;
    private final EmailService emailService;

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

    @GetMapping(path="/adminPanel")
    public String openAdminPanel(Model model, HttpSession session){
        String loggedInUsername = (String) session.getAttribute("user_username");
        String userId = (String) session.getAttribute("user_id");
        List<User> users=userService.getAllUsers();
        List<Listing> listings=listingService.getAll();
        if(userId == null || !userService.checkIfAdminByUserId(userId)){
            return "redirect:/notFound";
        }
        model.addAttribute("users", users);
        model.addAttribute("listings", listings);
        return "adminPanel";
    }



    @GetMapping(path = {"/user", "/user/{username}"})
    public String openUserPage(@PathVariable(name = "username", required = false) Optional<String> usernameOptional, Model model, HttpSession session) {
        String loggedInUsername = (String) session.getAttribute("user_username");
        String userId = (String) session.getAttribute("user_id");
        if (userId == null || loggedInUsername == null){
            return "redirect:/login";
        }

        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();

            User user = userService.findByUserName(username);
            if (user == null) {
                return "redirect:/notFound";
            }

            if (!username.equals(loggedInUsername)) {
                model.addAttribute("username", user.getUserName());
                if(user.getUserRating() == null){
                    model.addAttribute("rating", "No rating yet");
                }
                else{
                    model.addAttribute("rating", String.valueOf(user.getUserRating()));

                }
                model.addAttribute("premium", user.getIsPremium());
                return "otherUser";
            }
        }

        model.addAttribute("userId", userId);
        model.addAttribute("username", loggedInUsername);


        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(loggedInUsername));
        model.addAttribute("admin", userOptional.get().getIsAdmin());
        model.addAttribute("premium", userOptional.get().getIsPremium());


        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDTO = new UserDto();
            String[] names = user.getFullName().split(" ");
            userDTO.setFirstName(names[0]);
            userDTO.setSurname(names[1]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(user.getBirthDate());
            userDTO.setBirthDate(formattedDate);
            userDTO.setPhoneNumber(Integer.valueOf(user.getPhoneNumber()));
            userDTO.setUsername(user.getUserName());
            userDTO.setEmail(user.getEmail());

            model.addAttribute("userDTO", userDTO);
        } else {
            return "redirect:/notFound";
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

    @PostMapping(path = "/user/update")
    public String userUpdate(@ModelAttribute UserDto userDto, HttpSession session) throws ParseException {
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInUsername == null) {
            return "redirect:/login";
        }

        User existingUser = userService.findByUserName(loggedInUsername);
        if (existingUser == null) {
            return "notFound";
        }

        existingUser.setFullName(userDto.getFirstName() + " " + userDto.getSurname());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = dateFormat.parse(userDto.getBirthDate());
        existingUser.setBirthDate(parsedDate);
        existingUser.setPhoneNumber(userDto.getPhoneNumber().toString());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setUserName(userDto.getUsername());

        userService.saveUser(existingUser);

        return "redirect:/login";
    }

    @PostMapping(path = "/user/passwordChange")
    public String userPasswordChange(@RequestParam("old_password") String oldPassword,
                                     @RequestParam("new_pass") String newPassword,
                                     @RequestParam("new_pass2") String newPassword2,
                                     HttpSession session ,Model model)
    {
        try{
            String username = (String) session.getAttribute("user_username");

            User currentUser = userService.authenticateUser(username, oldPassword);

            String hashedNewPassword = HashService.generateSHA512(newPassword);
            currentUser.setHashedPassword(hashedNewPassword);
            userService.saveUser(currentUser);

            return "redirect:/login";
        }catch (RuntimeException e){
            model.addAttribute("alert_ch_pass", "Current Password is incorrect! Login again!");
            return "login";
        }

    }

    @PostMapping(path = "/login")
    public String loginValidation(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        try {
            User user = userService.authenticateUser(username,password);
            HttpSession session = request.getSession(true);
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
                model.addAttribute("alert_forgot_pass", "Forgot your password?");
                return "login";
            }
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();
        return "redirect:/login";
    }
    @PostMapping(path="/register")
    public String registrationCheck(@ModelAttribute UserDto userDto){
        User user = UserService.createUserFromDto(userDto);

        if (userService.isExistingCheck(user))
            System.out.println("handleanje postojeceg korisnika");  //  <--- handleat existing info
        else
            userService.registerUser(user);

        return "login";
    }


    @GetMapping("/editUser/{username}")
    public String editUser(@PathVariable("username") String username, Model model) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDto userDTO = new UserDto();
            String[] names = user.getFullName().split(" ");
            userDTO.setFirstName(names[0]);
            userDTO.setSurname(names[1]);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(user.getBirthDate());
            userDTO.setBirthDate(formattedDate);
            userDTO.setPhoneNumber(Integer.valueOf(user.getPhoneNumber()));
            userDTO.setUsername(user.getUserName());
            userDTO.setEmail(user.getEmail());
            userDTO.setAdmin(user.getIsAdmin());
            userDTO.setPremium(user.getIsPremium());

            model.addAttribute("userDTO", userDTO);
        } else {
            return "redirect:/notFound";
        }
        return "editUser";
    }

    @PostMapping("/editUser/update")
    public String updateUser(@ModelAttribute UserDto userDto) throws ParseException {
        String username = userDto.getUsername();
        System.out.println(username);

        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));

        if (!userOptional.isPresent()) {
            return "redirect:/notFound";
        }

        User user = userOptional.get();
        user.setIsAdmin(userDto.isAdmin());
        user.setIsPremium(userDto.isPremium());

        userService.saveUser(user);

        return "redirect:/adminPanel";
    }
    @PostMapping("/deleteUser/{username}")
    public String deleteUser(@PathVariable String username) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));
        if (!userOptional.isPresent()) {
            return "redirect:/notFound";
        }
        User user = userOptional.get();
        userService.deleteUser(user);
        return "redirect:/adminPanel";
    }

    @GetMapping(path="/mylistings")
    public String viewListings(Model model, HttpSession session)
    {
        String userId = (String) session.getAttribute("user_id");
        List<Listing> userListings = listingService.findByUserId(userId);
        model.addAttribute("listings",userListings);
        return "mylistings";
    }

    @GetMapping(path="/myMessages")
    public String viewMyMessages(Model model, HttpSession session)
    {
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInUsername == null) {
            return "redirect:/login";
        }
        User user=userService.findByUserName(loggedInUsername);
        List<Chat> chats = chatService.findByUserID(user);
        List<Chat> chatOwner = new ArrayList<>();
        List<Listing> listings = listingService.findByUserId(user.getId());

        for (Listing listing : listings) {
            Chat chat = chatService.findExistingChatByListings(listing);
            if (chat != null) {
                chatOwner.add(chat);
            }
        }

        model.addAttribute("chats",chats);
        model.addAttribute("chatOwner",chatOwner);


        return "messages";
    }

    @GetMapping(path="/recoverPassword")
    public String recoverPassword(){
        return "recover-password";
    }
    @PostMapping(path="/recoverPassword")
    public String dataValidation(@RequestParam String email, Model model) {
        User user = userService.findByEmail(email);
        if (user==null) {
            model.addAttribute("alert", "User not found!");
            return "recover-password";
        }
        String userId = String.valueOf(user.getId());
        String link="http://localhost:8080/password-recovery/"+userId;
        emailService.sendRecoveryEmail(user,link);
        model.addAttribute("alert2", "Recovery mail has been sent to your email!");
        return "recover-password";
    }

    @PostMapping(path="/premiumRequest")
    public String premiumRequest(HttpSession session) {
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInUsername == null) {
            return "redirect:/login";
        }
        User user = userService.findByUserName(loggedInUsername);
        emailService.sendPremiumAccRequest(user);
        return "redirect:/user";
    }

    @GetMapping(path="/password-recovery/{uuid}")
    public String passwordRecovery(@PathVariable("uuid") String uuid, Model model) {
        User user = userService.findById(uuid);

        if(user==null)
        {
            return "notFound";
        }
        String username = user.getUserName();
        model.addAttribute("username", username);
        return "change-password";
    }

    @PostMapping(path = "/password-recovery/update")
    public String passwordChange(@RequestParam("new_pass") String newPassword, @RequestParam("username") String username,
                                 Model model)
    {
        try{
            User user=userService.findByUserName(username);
            String hashedNewPassword = HashService.generateSHA512(newPassword);
            user.setHashedPassword(hashedNewPassword);
            userService.saveUser(user);
            return "redirect:/login";
        }catch (RuntimeException e){
            model.addAttribute("alert", e.getMessage());
            return "redirect:/notFound";
        }

    }
}




