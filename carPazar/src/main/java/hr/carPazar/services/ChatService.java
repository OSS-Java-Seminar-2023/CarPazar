package hr.carPazar.services;

import hr.carPazar.repositories.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

}