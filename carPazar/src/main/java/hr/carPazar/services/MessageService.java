package hr.carPazar.services;

import hr.carPazar.repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

}