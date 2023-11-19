package hr.carPazar.repositories;

import hr.carPazar.models.Chat.java;
import java.Util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, UUID>{

}