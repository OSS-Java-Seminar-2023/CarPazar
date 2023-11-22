package hr.carpazar.repositories;

import hr.carpazar.models.Review;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, UUID>{

}