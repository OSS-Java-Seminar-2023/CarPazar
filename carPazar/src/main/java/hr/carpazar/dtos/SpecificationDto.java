package hr.carpazar.dtos;

import lombok.Data;
import java.time.LocalDate;
import java.time.Year;

@Data
public class SpecificationDto {
    private String id;
    private String brand;
    private String model;
    private String enginePower;
    private String powerUnit;
    private String engineType;
    private String shifterType;
    private Integer kilometersTravelled;
    private Year manufactureYear;
    private Year inTrafficSince;
    private Integer doorCount;
    private Integer gearCount;
    private String location;
    private String bodyShape;
    private Boolean isUsed;
    private String driveType;
    private Double consumption;
    private String acType;
    private Integer seatCount;
    private LocalDate registrationUntil;
    private Integer ownerNo;
    private String color;
    private Integer additionalEquipment;
    private Integer extraFeatures;
}
