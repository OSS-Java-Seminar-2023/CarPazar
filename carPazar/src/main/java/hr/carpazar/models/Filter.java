package hr.carpazar.models;

import lombok.Getter;
import lombok.Setter;
import java.time.Year;

@Getter
@Setter
public class Filter {
    private int size;
    private int page;
    private String sort;
    private String brand;
    private String model;
    private int enginePowerMin;
    private int enginePowerMax;
    private String engineType;
    private String shifterType;
    private int kilometersTravelledMin;
    private int kilometersTravelledMax;
    private Year manufactureYearMin;
    private Year manufactureYearMax;
    private int ownerNo;
    private Boolean isUsed;
    private String location;
    private int doorCount;
    private int gearCount;
    private String bodyShape;
    private String driveType;
    private Double consumptionMin;
    private Double consumptionMax;
    private String acType;
    private int seatCount;
}
