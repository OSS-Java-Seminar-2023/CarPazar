package hr.carpazar.controllers;

import hr.carpazar.dtos.*;
import hr.carpazar.models.*;
import hr.carpazar.repositories.MessageRepository;
import hr.carpazar.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ListingService listingService;
    @Autowired
    private ChatService chatService;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private MessageService messageService;


    @GetMapping(path = "/{string}")
    public String nothing(String string) {
        return "redirect:/notFound";
    }

    @GetMapping({"/", "/home"})
    public String home(HttpSession httpSession, Model model) {
        String loggedInId = (String) httpSession.getAttribute("user_id");
        if(loggedInId==null)
            model.addAttribute("userid", "fragments/navbarNotLoggedIn");
        else
            model.addAttribute("userid", "fragments/navbar");



        List<Listing> listings = listingService.getAll();
        List<Listing> listingsFiltered =
                listings.stream()
                        .filter(listing -> listing.getIsSponsored())
                        .sorted((listing1, listing2)
                                -> listing2.getListingDatetime().compareTo(
                                listing1.getListingDatetime()))
                        .limit(4)
                        .collect(Collectors.toList());

        if (listingsFiltered.isEmpty()) {
            model.addAttribute("listings", null);
        } else {
            model.addAttribute("listings", listingsFiltered);
        }
        return "home";
    }

    @GetMapping(path = "/login")
    public String openLoginForm() {
        return "login";
    }

    @GetMapping(path = "/register")
    public String openRegistrationForm(Model model) {
        if(model.containsAttribute("exists"))
            model.addAttribute("exists", true);
        return "register";
    }

    @GetMapping(path = "/adminPanel")
    public String openAdminPanel(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("user_id");
        List<User> users = userService.getAllUsers();
        List<Listing> listings = listingService.getAll();
        if (userId == null || !userService.checkIfAdminByUserId(userId)) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        model.addAttribute("users", users);
        model.addAttribute("listings", listings);
        return "adminPanel";
    }

    @GetMapping(path = {"/user", "/user/{username}"})
    public String openUserPage(@PathVariable(name = "username", required = false) Optional<String> usernameOptional,
                               Model model, HttpSession session) {
        String loggedInId = (String) session.getAttribute("user_id");
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        if (usernameOptional.isPresent()) {
            String username = usernameOptional.get();

            Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));

            if (!userOptional.isPresent()) {
                return "redirect:/notFound";
            }

            User user = userOptional.get();

            if (!username.equals(loggedInUsername)) {
                model.addAttribute("username", user.getUserName());
                model.addAttribute("premium", user.getIsPremium());
                List<Listing> listings = listingService.findByUserId(user.getId());
                model.addAttribute("listings", listings);

                return "otherUser";
            }
        }

        model.addAttribute("userId", loggedInId);
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
    public String openOtherUserPage(HttpSession session, Model model) {
        String userid = (String) session.getAttribute("user_id");
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        return "otheruser";
    }

    @PostMapping(path = "/user/update")
    public String userUpdate(@ModelAttribute UserDto userDto, HttpSession session, Model model) throws ParseException {
        String loggedInID = (String) session.getAttribute("user_id");
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInID == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
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

        return "redirect:/logout";
    }

    @PostMapping(path = "/user/passwordChange")
    public String userPasswordChange(@RequestParam("old_password") String oldPassword,
                                     @RequestParam("new_pass") String newPassword, @RequestParam("new_pass2") String newPassword2,
                                     HttpSession session, Model model) {
        String loggedInUsername = (String) session.getAttribute("user_id");
        if (loggedInUsername == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        try {
            String username = (String) session.getAttribute("user_username");

            User currentUser = userService.authenticateUser(username, oldPassword);

            String hashedNewPassword = HashService.generateSHA512(newPassword);
            currentUser.setHashedPassword(hashedNewPassword);
            userService.saveUser(currentUser);

            return "redirect:/logout";
        } catch (RuntimeException e) {
            model.addAttribute("alert_ch_pass", "Current Password is incorrect! Login again!");
            return "logout";
        }
    }

    @PostMapping(path = "/login")
    public String loginValidation(
            @RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        try {
            User user = userService.authenticateUser(username, password);
            HttpSession session = request.getSession(true);
            session.setAttribute("user_id", user.getId());
            session.setAttribute("user_username", user.getUserName());
            return "redirect:/home";
        } catch (RuntimeException e) {
            if (!Objects.equals(e.getMessage(), "Wrong password!")) {
                model.addAttribute("alert", "User not found!");
                return "login";
            } else {
                model.addAttribute("alert", e.getMessage());
                model.addAttribute("alert_forgot_pass", "Forgot your password?");
                return "login";
            }
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }
    @PostMapping(path = "/register")
    public String registrationCheck(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) {
        User user = UserService.createUserFromDto(userDto);

        if (userService.isExistingCheck(user)) {
            redirectAttributes.addFlashAttribute("exists", true);
            return "redirect:/register";
        }
        else
            userService.registerUser(user);

        return "login";
    }

    @GetMapping("/editUser/{username}")
    public String editUser(@PathVariable("username") String username, Model model, HttpSession session) {
        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));
        String loggedInUsername =
                (session.getAttribute("user_id") != null) ? session.getAttribute("user_id").toString() : null;
        if (loggedInUsername == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

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
    public String updateUser(@ModelAttribute UserDto userDto, HttpSession session, Model model) throws ParseException {
        String loggedInUsername = (String) session.getAttribute("user_id");
        if (loggedInUsername == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        String username = userDto.getUsername();

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
    @RequestMapping("/deleteUser/{username}")
    @Transactional
    public String deleteUser(@PathVariable String username, Model model, HttpSession session) {
        String loggedInUsername = (String) session.getAttribute("user_id");
        User admin = userService.findById(loggedInUsername);
        if (loggedInUsername == null || !admin.getIsAdmin()) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<User> userOptional = Optional.ofNullable(userService.findByUserName(username));
        if (!userOptional.isPresent()) {
            return "redirect:/notFound";
        }
        User user=userOptional.get();
        List<Listing> listings = listingService.findByUserId(user.getId());

        listings.forEach(listing -> {
            List<Chat> chats = chatService.findAllChatsByListing(listing);

            chats.forEach(chat -> {
                messageService.deleteByChatId(chat);
                chatService.deleteById(chat.getId());
            });

            listingService.deleteListing(listing);
        });

        List<Chat> chats = chatService.findAllChatsByBuyerId(user);
        for(Chat chat:chats) {
            messageService.deleteByChatId(chat);
            chatService.deleteById(chat.getId());
        }

        userService.deleteUser(user);
        return "redirect:/adminPanel";
    }

    @GetMapping(path = "/mylistings")
    public String viewListings(Model model, HttpSession httpSession, @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        String loggedInId = (String) httpSession.getAttribute("user_id");
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        List<Listing> listings = listingService.findByUserId(loggedInId);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Listing> listingPage =
                listingService.findPaginatedByUser(PageRequest.of(currentPage - 1, pageSize), loggedInId);

        int totalPages = listingPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("listingPage", listingPage);
        model.addAttribute("listings", listings);
        return "mylistings";
    }

    @GetMapping(path = "/recoverPassword")
    public String recoverPassword(HttpSession session, Model model) {
        return "recover-password";
    }
    @PostMapping(path = "/recoverPassword")
    public String dataValidation(@RequestParam String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("alert", "User not found!");
            return "recover-password";
        }
        String userId = String.valueOf(user.getId());
        String link = "http://localhost:8080/password-recovery/" + userId;
        emailService.sendRecoveryEmail(user, link);
        model.addAttribute("alert2", "Recovery mail has been sent to your email!");
        return "recover-password";
    }

    @PostMapping(path = "/premiumRequest")
    public String premiumRequest(HttpSession session, Model model) {
        String loggedInId = (String) session.getAttribute("user_id");
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        User user = userService.findByUserName(loggedInUsername);
        emailService.sendPremiumAccRequest(user);
        return "redirect:/user";
    }

    @GetMapping(path = "/password-recovery/{uuid}")
    public String passwordRecovery(@PathVariable("uuid") String uuid, Model model) {
        User user = userService.findById(uuid);

        if (user == null) {
            return "notFound";
        }
        String username = user.getUserName();
        model.addAttribute("username", username);
        return "change-password";
    }

    @PostMapping(path = "/password-recovery/update")
    public String passwordChange(
            @RequestParam("new_pass") String newPassword, @RequestParam("username") String username, Model model) {
        try {
            User user = userService.findByUserName(username);
            String hashedNewPassword = HashService.generateSHA512(newPassword);
            user.setHashedPassword(hashedNewPassword);
            userService.saveUser(user);
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("alert", e.getMessage());
            return "redirect:/notFound";
        }
    }
}
