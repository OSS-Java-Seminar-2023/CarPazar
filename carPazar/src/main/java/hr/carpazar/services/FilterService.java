package hr.carpazar.services;

import hr.carpazar.dtos.FilterDto;
import hr.carpazar.models.Filter;
import hr.carpazar.models.Specification;
import hr.carpazar.repositories.SpecificationRepository;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterService {
    @Autowired
    private SpecificationRepository specificationRepository;
    public Filter setDefaults(FilterDto filterDto) {
        Filter filters = new Filter();
        String[] parts;

        if (filterDto.getSize() != 5)
            filters.setSize(5);
        else
            filters.setSize(5);

        if (filterDto.getPage().isEmpty())
            filters.setPage(1);
        else {
            filters.setPage(1);
        }

        if (Objects.equals(filterDto.getSort(), "any"))
            filters.setSort("dateAsc");
        else
            filters.setSort(filterDto.getSort());

        if (filterDto.getEnginePowerMin().isEmpty())
            filters.setEnginePowerMin(0);
        else
            filters.setEnginePowerMin(Integer.parseInt(filterDto.getEnginePowerMin()));

        if (filterDto.getEnginePowerMax().isEmpty())
            filters.setEnginePowerMax(999);
        else
            filters.setEnginePowerMax(Integer.parseInt(filterDto.getEnginePowerMax()));

        if (filterDto.getKilometersTravelledMin().isEmpty()) {
            filters.setKilometersTravelledMin(0);
        } else
            filters.setKilometersTravelledMin(Integer.parseInt(filterDto.getKilometersTravelledMin()));

        if (filterDto.getKilometersTravelledMax().isEmpty())
            filters.setKilometersTravelledMax(9999999);
        else
            filters.setKilometersTravelledMax(Integer.parseInt(filterDto.getKilometersTravelledMax()));

        if (filterDto.getManufactureYearMin().isEmpty())
            filters.setManufactureYearMin(Year.parse("1900"));
        else
            filters.setManufactureYearMin(Year.parse(filterDto.getManufactureYearMin()));

        if (filterDto.getManufactureYearMax().isEmpty())
            filters.setManufactureYearMax(Year.parse("2100"));
        else
            filters.setManufactureYearMax(Year.parse(filterDto.getManufactureYearMax()));

        if (filterDto.getIsUsed() == null)
            filters.setIsUsed(null);
        else
            filters.setIsUsed(Boolean.parseBoolean(filterDto.getIsUsed()));

        if (filterDto.getDoorCount().isEmpty())
            filters.setDoorCount(333);
        else
            filters.setDoorCount(Integer.parseInt(filterDto.getDoorCount()));

        if (filterDto.getGearCount().isEmpty())
            filters.setGearCount(333);
        else
            filters.setDoorCount(Integer.parseInt(filterDto.getDoorCount()));

        if (filterDto.getConsumptionMin().isEmpty())
            filters.setConsumptionMin(0d);
        else
            filters.setConsumptionMin(Double.parseDouble(filterDto.getConsumptionMin()));

        if (filterDto.getConsumptionMax().isEmpty())
            filters.setConsumptionMax(999999d);
        else
            filters.setConsumptionMax(Double.parseDouble(filterDto.getConsumptionMax()));

        if (filterDto.getSeatCount().isEmpty())
            filters.setSeatCount(333);
        else
            filters.setSeatCount(Integer.parseInt(filterDto.getSeatCount()));

        if (filterDto.getOwnerNo().equals("any"))
            filters.setOwnerNo(333);
        else
            filters.setOwnerNo(Integer.parseInt(filterDto.getOwnerNo()));

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

    public List<Specification> findSpecificationByFilter(Filter filter) {
        String brand = filter.getBrand();
        String model = filter.getModel();
        int enginePowerMin = filter.getEnginePowerMin();
        int enginePowerMax = filter.getEnginePowerMax();
        String engineType = filter.getEngineType();
        String shifterType = filter.getShifterType();
        int kilometersTravelledMin = filter.getKilometersTravelledMin();
        int kilometersTravelledMax = filter.getKilometersTravelledMax();
        Year manufactureYearMin = filter.getManufactureYearMin();
        Year manufactureYearMax = filter.getManufactureYearMax();
        int ownerNo = filter.getOwnerNo();
        Boolean isUsed = filter.getIsUsed();
        String location = filter.getLocation();
        int doorCount = filter.getDoorCount();
        int gearCount = filter.getGearCount();
        String bodyShape = filter.getBodyShape();
        String driveType = filter.getDriveType();
        Double consumptionMin = filter.getConsumptionMin();
        Double consumptionMax = filter.getConsumptionMax();
        String acType = filter.getAcType();
        int seatCount = filter.getSeatCount();

        return specificationRepository.findByArguments(brand, model, enginePowerMin, enginePowerMax, engineType,
                shifterType, kilometersTravelledMin, kilometersTravelledMax, manufactureYearMin, manufactureYearMax,
                ownerNo, isUsed, location, doorCount, gearCount, bodyShape, driveType, consumptionMin, consumptionMax,
                acType, seatCount);
    }
}
