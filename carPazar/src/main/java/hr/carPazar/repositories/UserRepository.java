package hr.carPazar.repositories;

import hr.carPazar.models.User.java;
import java.Util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID>{

}