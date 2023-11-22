package hr.carpazar.repositories;

import hr.carpazar.models.Message;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, UUID>{

}