package hr.carpazar.dtos;

import lombok.Data;

@Data
public class FilterDto {
    private final int size = 5;
    private String page;
    private String sort;
    private String brand;
    private String model;
    private String enginePowerMin;
    private String enginePowerMax;
    private String engineType;
    private String shifterType;
    private String kilometersTravelledMin;
    private String kilometersTravelledMax;
    private String manufactureYearMin;
    private String manufactureYearMax;
    private String ownerNo;
    private String isUsed;
    private String location;
    private String doorCount;
    private String gearCount;
    private String bodyShape;
    private String driveType;
    private String consumptionMin;
    private String consumptionMax;
    private String acType;
    private String seatCount;
}

