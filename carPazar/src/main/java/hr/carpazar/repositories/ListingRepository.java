package hr.carpazar.repositories;

import hr.carpazar.models.Listing;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing, UUID>{

    List<Listing> findByUserId_Id(String id);
    List<Listing> findById(String id);
    List<Listing> findAllByOrderByPriceDesc();
    List<Listing> findAllByOrderByPriceAsc();
    List<Listing> findAllByOrderByListingDatetimeDesc();
    List<Listing> findAllByOrderByListingDatetimeAsc();


}