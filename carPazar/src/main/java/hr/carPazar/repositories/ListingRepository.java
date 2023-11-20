package hr.carPazar.repositories;

import hr.carPazar.models.Listing;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ListingRepository extends JpaRepository<Listing, UUID>{

}