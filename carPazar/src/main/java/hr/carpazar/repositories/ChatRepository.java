package hr.carpazar.repositories;

import hr.carpazar.models.Chat;
import java.util.List;
import java.util.Optional;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, String> {
    Optional<Chat> findByBuyerIdAndListingId(User user , Listing listing);
    List<Chat> findByBuyerId(User user);
    Chat findByListingId(Listing listing);
    List<Chat> findAllByListingId(Listing listing);
    List<Chat> findAllByBuyerId(User buyerId);
    void deleteByBuyerId(User buyer);
    void deleteByListingId(Listing listingId);
    void deleteById(String id);

}
