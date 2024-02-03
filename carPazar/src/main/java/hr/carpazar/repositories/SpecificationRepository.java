package hr.carpazar.repositories;

import hr.carpazar.models.Specification;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SpecificationRepository extends JpaRepository<Specification, UUID>{

    Specification findById(String listingId);

    @Query("SELECT s FROM Specification s WHERE (:brand = 'any' OR s.brand = :brand) AND" +
            "(:model = 'any' OR s.model = :model) AND" +
            "(s.enginePower BETWEEN :enginePowerMin AND :enginePowerMax) AND" +
            "(:engineType = 'any' OR s.engineType = :engineType) AND" +
            "(:shifterType = 'any' OR s.shifterType = :shifterType) AND" +
            "(s.kilometersTravelled BETWEEN :kilometersTravelledMin AND :kilometersTravelledMax) AND" +
            "(s.manufactureYear BETWEEN :manufactureYearMin AND :manufactureYearMax) AND" +
            "(:ownerNo = 333 OR s.ownerNo = :ownerNo) AND" +
            "(:isUsed IS NULL OR s.isUsed = :isUsed) AND" +
            "(:location = 'any' OR s.location = :location) AND" +
            "(:doorCount = 333 OR s.doorCount = :doorCount) AND" +
            "(:gearCount = 333 OR s.gearCount = :gearCount) AND" +
            "(:bodyShape = 'any' OR s.bodyShape = :bodyShape) AND" +
            "(:driveType = 'any' OR s.driveType = :driveType) AND" +
            "(s.consumption BETWEEN :consumptionMin and :consumptionMax) AND" +
            "(:acType = 'any' OR s.acType = :acType) AND" +
            "(:seatCount = 333 OR s.seatCount = :seatCount)")
    List<Specification> findByArguments(String brand, String model, int enginePowerMin, int enginePowerMax,
                                        String engineType, String shifterType, int kilometersTravelledMin,
                                        int kilometersTravelledMax, Year manufactureYearMin, Year manufactureYearMax,
                                        int ownerNo, Boolean isUsed, String location, int doorCount, int gearCount, String bodyShape, String driveType,
                                        Double consumptionMin, Double consumptionMax, String acType, int seatCount);

}