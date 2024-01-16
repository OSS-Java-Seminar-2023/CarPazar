package hr.carpazar.repositories;

import hr.carpazar.models.Chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<Chat, String> {
    Optional<Chat> findByBuyerIdAndListingId(User user , Listing listing);
    List<Chat> findByBuyerId(User user);

    Chat findByListingId(Listing listing);
}
