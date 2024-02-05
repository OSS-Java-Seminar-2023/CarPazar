package hr.carpazar.services;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Message;
import hr.carpazar.repositories.MessageRepository;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired private MessageRepository messageRepository;

    public Map<LocalDateTime, String> findByChatID(String chatID) {
        List<Message> messages = messageRepository.findByChatId_IdOrderByMessageDatetime(chatID);

        return messages.stream().collect(Collectors.toMap(Message::getMessageDatetime,
                Message::getMessageContent,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public List<Message> findAllByChatId(Chat chat){
        return messageRepository.findAllByChatId(chat);
    }
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteByChatId(Chat id){messageRepository.deleteByChatId(id);}
    public List<Message> getAll() {
        return messageRepository.findAll();
    }
}