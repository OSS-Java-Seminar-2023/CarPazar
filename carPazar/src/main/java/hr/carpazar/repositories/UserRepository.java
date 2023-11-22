package hr.carpazar.repositories;

import hr.carpazar.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID>{

}