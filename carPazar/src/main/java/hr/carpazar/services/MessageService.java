package hr.carpazar.services;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Listing;
import hr.carpazar.models.Message;
import hr.carpazar.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Map<LocalDateTime, String> findByChatID(String chatID) {
        List<Message> messages = messageRepository.findByChatId_IdOrderByMessageDatetime(chatID);

        return messages.stream()
                .collect(Collectors.toMap(
                        Message::getMessageDatetime, //kljuc
                        Message::getMessageContent, //vrijednost
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteByChatId(Chat chat) {
        List<Message> messages = messageRepository.findAllByChatId(chat);
        for (Message msg : messages) {
            System.out.println("msg in"+msg.getMessageContent());
            messageRepository.deleteByChatId(chat);
        }
    }

    public List<Message> getAll(){return messageRepository.findAll();}

}