package hr.carPazar.repositories;

import hr.carPazar.models.Message;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, UUID>{

}