package hr.carPazar.repositories;

import hr.carPazar.models.Specification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SpecificationRepository extends JpaRepository<Specification, UUID>{

}