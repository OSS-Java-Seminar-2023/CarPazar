package hr.carpazar.repositories;

import hr.carpazar.models.Review;
import java.util.UUID;

import hr.carpazar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, UUID>{

    void deleteByUser(User user);
}