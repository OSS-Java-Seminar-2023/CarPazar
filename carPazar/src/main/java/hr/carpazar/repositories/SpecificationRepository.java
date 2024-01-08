package hr.carpazar.repositories;

import hr.carpazar.models.Specification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpecificationRepository extends JpaRepository<Specification, UUID>{

    Specification findById(String listingId);
}