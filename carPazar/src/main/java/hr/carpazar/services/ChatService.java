package hr.carpazar.services;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.repositories.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public Optional<Chat> findByID(String chatID) {
        return chatRepository.findById(chatID);
    }

    public Optional<Chat> findExistingChat(User user, Listing listing) {
        return chatRepository.findByBuyerIdAndListingId(user, listing);
    }
    public Chat saveChat(Chat chat){
        chatRepository.save(chat);
        return chat;
    }
}