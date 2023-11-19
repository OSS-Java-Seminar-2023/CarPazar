package hr.carPazar.repositories;

import hr.carPazar.models.Message.java;
import java.Util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepository extends JpaRepository<Message, UUID>{

}