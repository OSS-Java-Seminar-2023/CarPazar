package hr.carpazar.services;

import hr.carpazar.dtos.FilterDto;
import hr.carpazar.models.Filter;
import hr.carpazar.models.Listing;
import hr.carpazar.models.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Service
public class FilterService {
    public Filter setDefaults(FilterDto filterDto){
        Filter filters = new Filter();

        if (filterDto.getSize() != 5)
            filters.setSize(5);
        else
            filters.setSize(5);

        if (filterDto.getPage().isEmpty())
            filters.setPage(1);
        else
            filters.setPage(Integer.parseInt(filterDto.getPage()));

        if (filterDto.getEnginePowerMin().isEmpty())
            filters.setEnginePowerMin(0);
        else
            filters.setPage(Integer.parseInt(filterDto.getEnginePowerMin()));

        if (filterDto.getEnginePowerMax().isEmpty())
            filters.setEnginePowerMax(999);
        else
            filters.setPage(Integer.parseInt(filterDto.getEnginePowerMax()));

        if (filterDto.getKilometersTravelledMin().isEmpty()){
            System.out.println("ulazim u empty");
            filters.setKilometersTravelledMin(0);}
        else
            filters.setKilometersTravelledMin(Integer.parseInt(filterDto.getKilometersTravelledMin()));

        if (filterDto.getKilometersTravelledMax().isEmpty())
            filters.setKilometersTravelledMax(9999999);
        else
            filters.setPage(Integer.parseInt(filterDto.getKilometersTravelledMax()));

        if (filterDto.getManufactureYearMin().isEmpty())
            filters.setManufactureYearMin(Year.parse("1900"));
        else
            filters.setManufactureYearMin(Year.parse(filterDto.getManufactureYearMin()));

        if (filterDto.getManufactureYearMax().isEmpty())
            filters.setManufactureYearMax(Year.parse("2100"));
        else
            filters.setManufactureYearMax(Year.parse(filterDto.getManufactureYearMax()));

        if (filterDto.getRegistrationUntil().isEmpty())
            filters.setRegistrationUntil(LocalDate.parse("0001-01-01"));
        else
            filters.setRegistrationUntil(LocalDate.parse(filterDto.getRegistrationUntil()));

        if (filterDto.getIsUsed() == null)
            filters.setIsUsed(null);  // boolean null -> any
        else
            filters.setIsUsed(Boolean.parseBoolean(filterDto.getIsUsed()));

        if (filterDto.getDoorCount().isEmpty())
            filters.setDoorCount(333); // if 333 u filterima -> any
        else
            filters.setDoorCount(Integer.parseInt(filterDto.getDoorCount()));

        if (filterDto.getConsumptionMin().isEmpty())
            filters.setConsumptionMin(0d);
        else
            filters.setConsumptionMin(Double.parseDouble(filterDto.getConsumptionMin()));

        if (filterDto.getConsumptionMax().isEmpty())
            filters.setConsumptionMax(0d);
        else
            filters.setConsumptionMax(Double.parseDouble(filterDto.getConsumptionMax()));

        if (filterDto.getSeatCount().isEmpty())
            filters.setSeatCount(333); // 333 -> any
        else
            filters.setSeatCount(Integer.parseInt(filterDto.getSeatCount()));

        if (filterDto.getOwnerNo().equals("any"))
            filters.setOwnerNo(333); // 333 -> any
        else
            filters.setOwnerNo(Integer.parseInt(filterDto.getOwnerNo()));

        // non-empty fields
        filters.setBrand(filterDto.getBrand());
        filters.setModel(filterDto.getModel());
        filters.setSort(filterDto.getSort());
        filters.setEngineType(filterDto.getEngineType());
        filters.setShifterType(filterDto.getShifterType());
        filters.setLocation(filterDto.getLocation());
        filters.setBodyShape(filterDto.getBodyShape());
        filters.setDriveType(filterDto.getDriveType());
        filters.setAcType(filterDto.getAcType());

        return filters;
    }

    public List<Specification> findSpecificationByFilter(Filter filter){
        int size;
        int page;
        String sort;
        String brand;
        String model;
        int enginePowerMin;
        int enginePowerMax;
        String engineType;
        String shifterType;
        int kilometersTravelledMin;
        int kilometersTravelledMax;
        Year manufactureYearMin;
        Year manufactureYearMax;
        LocalDate registrationUntil;
        int ownerNo;
        Boolean isUsed;
        String location;
        int doorCount;
        int gearCount;
        String bodyShape;
        String driveType;
        Double consumptionMin;
        Double consumptionMax;
        String acType;
        int seatCount;
    }
}
