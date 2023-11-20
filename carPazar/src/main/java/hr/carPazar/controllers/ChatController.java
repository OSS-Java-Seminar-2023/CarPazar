package hr.carPazar.controllers;

import hr.carPazar.services.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
}