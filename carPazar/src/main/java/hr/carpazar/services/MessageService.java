package hr.carpazar.services;

import hr.carpazar.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

}