package hr.carpazar.repositories;

import hr.carpazar.models.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, UUID>{
    int countByUserName(String username);
    int countById(String id);
    int countByEmail(String email);
    List<User> findByUserName(String username);
    List<User> findByEmail(String email);
    List<User> findByid(String id);

}