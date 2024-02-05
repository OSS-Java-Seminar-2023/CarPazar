package hr.carpazar.controllers;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.services.ChatService;
import hr.carpazar.services.ListingService;
import hr.carpazar.services.MessageService;
import hr.carpazar.services.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ListingService listingService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(path = "/myMessages")
    public String viewMyMessages(Model model, HttpSession session) {
        String loggedInId = (String) session.getAttribute("user_id");
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        User user = userService.findByUserName(loggedInUsername);
        List<Chat> chats = chatService.findByUserID(user);
        List<Listing> listings = listingService.findByUserId(user.getId());
        List<Chat> chatOwner = new ArrayList<>();

        for (Listing listing : listings) {
            List<Chat> allChats = chatService.findExistingChatsByListings(listing);
            for (Chat chat : allChats) {
                chatOwner.add(chat);
            }
        }

        model.addAttribute("chats", chats);
        model.addAttribute("chatOwner", chatOwner);

        return "messages";
    }
}