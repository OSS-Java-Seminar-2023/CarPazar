package hr.carpazar.repositories;

import hr.carpazar.models.Chat;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, UUID>{

}