package hr.carpazar.controllers;

import hr.carpazar.services.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
}