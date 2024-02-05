package hr.carpazar.controllers;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.Message;
import hr.carpazar.models.User;
import hr.carpazar.services.ChatService;
import hr.carpazar.services.ListingService;
import hr.carpazar.services.MessageService;
import hr.carpazar.services.UserService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private ListingService listingService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/start-chat/{listingId}")
    public String startChatWithSeller(@PathVariable(name = "listingId", required = true) String listingId,
                                      HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String loggedInUser = (String) session.getAttribute("user_id");
        if (loggedInUser == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        User seller = listingService.findSellerByListingID(listingId);
        if (seller.getId() == null || seller.getId().equals(loggedInUser)) {
            return "redirect:/notFound";
        }
        User user = userService.findById(loggedInUser);
        Listing listing = listingService.findById(listingId);
        Optional<Chat> existingChat = chatService.findExistingChat(user, listing);
        if (existingChat.isPresent()) {
            String existingChatId = existingChat.get().getId();
            redirectAttributes.addAttribute("chatid", existingChatId);
            return "redirect:/chat/{chatid}";
        }

        Chat newChat = new Chat();
        newChat.setBuyerId(user);
        newChat.setListingId(listing);

        Chat savedChat = chatService.saveChat(newChat);
        String chatId = savedChat.getId();

        redirectAttributes.addAttribute("chatid", chatId);
        return "redirect:/chat/{chatid}";
    }

    @GetMapping(path = "/chat/{chatid}")
    public String openChat(@PathVariable("chatid") String chatid, Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("user_id");
        if (loggedInUser == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<Chat> chatOptional = chatService.findByID(chatid);

        if (chatOptional.isPresent()) {
            Chat chat = chatOptional.get();
            String listingID = String.valueOf(chat.getListingId().getId());
            String buyerID = String.valueOf(chat.getBuyerId().getId());
            User buyer = userService.findById(buyerID);
            User seller = listingService.findSellerByListingID(listingID);
            Listing listing = listingService.findById(listingID);

            Map<LocalDateTime, String> messages = messageService.findByChatID(chatid);

            model.addAttribute("chat", chat);
            model.addAttribute("messages", messages);
            model.addAttribute("loggedInUser", loggedInUser);

            if (loggedInUser.equals(buyerID)) {
                model.addAttribute("user_right", buyer.getId());
                model.addAttribute("user_right_username", buyer.getUserName());
                model.addAttribute("user_left", seller.getId());
                model.addAttribute("user_left_username", seller.getUserName());
                model.addAttribute("listing", listing);
                return "chat";
            } else if (loggedInUser.equals(seller.getId())) {
                model.addAttribute("user_right", seller.getId());
                model.addAttribute("user_right_username", seller.getUserName());
                model.addAttribute("user_left", buyer.getId());
                model.addAttribute("user_left_username", buyer.getUserName());
                model.addAttribute("listing", listing);
                return "chat";
            } else {
                return "redirect:/notFound";
            }
        }
        return "redirect:/notFound";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String messageText, @RequestParam("chatId") String chatId,
                              @RequestParam("sender") String senderID, Model model, HttpSession session,
                              RedirectAttributes redirectAttributes) {
        String loggedInUser = (String) session.getAttribute("user_id");
        if (loggedInUser == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<Chat> chat = chatService.findByID(chatId);
        if (!chat.isPresent()) {
            return "redirect:/notFound";
        }
        String fullMessage = loggedInUser + messageText;

        Message newMessage = new Message();
        newMessage.setMessageContent(fullMessage);
        newMessage.setMessageDatetime(LocalDateTime.now());
        newMessage.setChatId(chat.get());

        messageService.saveMessage(newMessage);

        redirectAttributes.addAttribute("chatid", chatId);
        return "redirect:/chat/{chatid}";
    }
}