package hr.carpazar.repositories;

import hr.carpazar.models.Listing;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ListingRepository extends JpaRepository<Listing, UUID>{

}