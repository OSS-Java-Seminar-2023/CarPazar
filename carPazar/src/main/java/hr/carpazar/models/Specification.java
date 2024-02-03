package hr.carpazar.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.Year;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Specification {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "brand", nullable = false, length = 32)
    private String brand;

    @Column(name = "model", nullable = false, length = 32)
    private String model;

    @Column(name = "engine_power", nullable = false)
    private Integer enginePower;

    @Column(name = "engine_type", nullable = false, length = 16)
    private String engineType;

    @Column(name = "shifter_type", nullable = false, length = 32)
    private String shifterType;

    @Column(name = "kilometers_travelled", nullable = false)
    private Integer kilometersTravelled;

    @Column(name = "manufacture_year", nullable = false)
    private Year manufactureYear;

    @Column(name = "in_traffic_since")
    private Year inTrafficSince;

    @Column(name = "door_count")
    private Integer doorCount;

    @Column(name = "gear_count")
    private Integer gearCount;

    @Column(name = "location", nullable = false, length = 64)
    private String location;

    @Column(name = "body_shape", length = 16)
    private String bodyShape;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @Column(name = "drive_type", length = 32)
    private String driveType;

    @Column(name = "consumption", columnDefinition = "DECIMAL(10,1)")
    private Double consumption;

    @Column(name = "ac_type", length = 32)
    private String acType;

    @Column(name = "seat_count")
    private Integer seatCount;

    @Column(name = "registration_until", nullable = false)
    private LocalDate registrationUntil;

    @Column(name = "owner_no", nullable = false)
    private Integer ownerNo;

    @Column(name = "color", length = 32)
    private String color;

    @Column(name = "additional_equipment")
    private Integer additionalEquipment;

    @Column(name = "extra_features")
    private Integer extraFeatures;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id")
    private hr.carpazar.models.Listing listing;

}
