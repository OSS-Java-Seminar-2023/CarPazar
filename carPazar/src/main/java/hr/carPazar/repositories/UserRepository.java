package hr.carPazar.repositories;

import hr.carPazar.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID>{

}