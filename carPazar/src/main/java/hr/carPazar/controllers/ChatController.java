package hr.carPazar.controllers;

import hr.carPazar.repositories.ChatService.java

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;
}