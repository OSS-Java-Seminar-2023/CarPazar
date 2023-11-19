package hr.carPazar.repositories;

import hr.carPazar.repositories.ChatRepository.java;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

}