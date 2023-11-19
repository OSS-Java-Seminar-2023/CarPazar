package hr.carPazar.repositories;

import hr.carPazar.models.Review.java;
import java.Util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, UUID>{

}