package hr.carpazar.repositories;

import hr.carpazar.models.Chat;
import hr.carpazar.models.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, UUID>{

    List<Message> findByChatId_IdOrderByMessageDatetime(String chatId);
    void deleteByChatId(Chat chatId);
    List<Message> findAllByChatId(Chat chatId);

}