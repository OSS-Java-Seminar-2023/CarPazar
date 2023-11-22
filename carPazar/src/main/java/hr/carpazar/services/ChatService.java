package hr.carpazar.services;

import hr.carpazar.repositories.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

}