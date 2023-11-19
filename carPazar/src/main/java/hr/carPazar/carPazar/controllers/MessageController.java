package hr.carPazar.controllers;

import hr.carPazar.repositories.MessageService.java

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
}