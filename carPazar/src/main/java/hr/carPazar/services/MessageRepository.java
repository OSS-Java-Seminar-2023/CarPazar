package hr.carPazar.repositories;
import hr.carPazar.repositories.MessageRepository.java;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

}