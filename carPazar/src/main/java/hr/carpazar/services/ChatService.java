package hr.carpazar.services;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.repositories.ChatRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public Optional<Chat> findByID(String chatID) {
        return chatRepository.findById(chatID);
    }

    public List<Chat> findByUserID(User user) {
        return chatRepository.findByBuyerId(user);
    }

    public Optional<Chat> findExistingChat(User user, Listing listing) {
        return chatRepository.findByBuyerIdAndListingId(user, listing);
    }

    public Chat findExistingChatByListings(Listing listing) {
        return chatRepository.findByListingId(listing);
    }
    public List<Chat> findExistingChatsByListings(Listing listing) {
        return chatRepository.findAllByListingId(listing);
    }


    public List<Chat> findAllChatsByListing(Listing listing) {
        return chatRepository.findAllByListingId(listing);
    }

    public List<Chat> findAllChatsByBuyerId(User buyerId) {
        return chatRepository.findAllByBuyerId(buyerId);
    }
    public Chat saveChat(Chat chat) {
        chatRepository.save(chat);
        return chat;
    }

    public void deleteByBuyerId(User buyerId) {
        chatRepository.deleteByBuyerId(buyerId);
    }

    public void deleteByListingId(Listing listing) {
        List<Chat> chats = chatRepository.findAllByListingId(listing);
        chats.forEach(chat -> chatRepository.deleteByListingId(listing));
    }

    public List<Chat> getAll() {
        return chatRepository.findAll();
    }
    public void deleteById(String id){chatRepository.deleteById(id);}
}