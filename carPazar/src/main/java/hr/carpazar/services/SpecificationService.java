package hr.carpazar.services;

import hr.carpazar.dtos.SpecificationDto;
import hr.carpazar.models.Specification;
import hr.carpazar.repositories.SpecificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;


@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository specificationRepository;

    public Specification createSpecificationFromDto(SpecificationDto specificationDto, String listingid){
        Map<String, String> postParams = new Hashtable<>();
        nullSpecificationHandle(specificationDto);
        /*
        *
        *       uspjesno pise specifikacije u bazu
        *       napravit kalkulaciju extra featurea i tih sranja
        *
        *
        * */
        postParams.put("id", listingid);
        postParams.put("brand", specificationDto.getBrand());
        postParams.put("model", specificationDto.getModel());
        postParams.put("enginePower", specificationDto.getEnginePower());
        postParams.put("engineType", specificationDto.getEngineType());
        postParams.put("shifterType", specificationDto.getShifterType());
        postParams.put("kilometersTravelled", specificationDto.getKilometersTravelled().toString());
        postParams.put("manufactureYear", specificationDto.getManufactureYear().toString());
        postParams.put("inTrafficSince", specificationDto.getInTrafficSince().toString());
        postParams.put("doorCount", specificationDto.getDoorCount().toString());
        postParams.put("gearCount", specificationDto.getGearCount().toString());
        postParams.put("location", specificationDto.getLocation());
        postParams.put("bodyShape", specificationDto.getBodyShape());
        postParams.put("isUsed", specificationDto.getIsUsed().toString());
        postParams.put("driveType", specificationDto.getDriveType());
        postParams.put("consumption", specificationDto.getConsumption().toString());
        postParams.put("acType", specificationDto.getAcType());
        postParams.put("seatCount", specificationDto.getSeatCount().toString());
        postParams.put("registrationUntil", specificationDto.getRegistrationUntil().toString());
        postParams.put("ownerNo", specificationDto.getOwnerNo().toString());
        postParams.put("color", specificationDto.getColor());
        postParams.put("additionalEquipment", specificationDto.getAdditionalEquipment().toString());
        postParams.put("extraFeatures", specificationDto.getExtraFeatures().toString());

        return createSpecification(postParams);
    }


    public Specification createSpecification(Map<String, String> specsData){
        Specification specs = new Specification();

        specs.setId(specsData.get("id"));
        specs.setBrand(specsData.get("brand"));
        specs.setModel(specsData.get("model"));
        specs.setEnginePower(Integer.parseInt(specsData.get("enginePower")));
        specs.setEngineType(specsData.get("engineType"));
        specs.setShifterType(specsData.get("shifterType"));
        specs.setKilometersTravelled(Integer.parseInt(specsData.get("kilometersTravelled")));
        specs.setManufactureYear(Year.parse(specsData.get("manufactureYear")));
        specs.setInTrafficSince(Year.parse(specsData.get("inTrafficSince")));
        specs.setDoorCount(Integer.parseInt(specsData.get("doorCount")));
        specs.setGearCount(Integer.parseInt(specsData.get("gearCount")));
        specs.setLocation(specsData.get("location"));
        specs.setBodyShape(specsData.get("bodyShape"));
        specs.setIsUsed(Boolean.parseBoolean(specsData.get("isUsed")));
        specs.setDriveType(specsData.get("driveType"));
        specs.setConsumption(Long.parseLong(specsData.get("consumption")));
        specs.setAcType(specsData.get("acType"));
        specs.setSeatCount(Integer.parseInt(specsData.get("seatCount")));
        specs.setRegistrationUntil(LocalDate.parse(specsData.get("registrationUntil")));
        specs.setOwnerNo(Integer.parseInt(specsData.get("ownerNo")));
        specs.setColor(specsData.get("color"));
        specs.setAdditionalEquipment(Integer.parseInt(specsData.get("additionalEquipment")));
        specs.setExtraFeatures(Integer.parseInt(specsData.get("extraFeatures")));

        return specs;
    }

    private void nullSpecificationHandle(SpecificationDto specificationDto){
        if(specificationDto.getInTrafficSince() == null)
            specificationDto.setInTrafficSince(Year.parse("0"));

        if(specificationDto.getDoorCount() == null)
            specificationDto.setDoorCount(0);

        if(specificationDto.getGearCount() == null)
            specificationDto.setGearCount(0);

        if(specificationDto.getBodyShape() == null)
            specificationDto.setBodyShape("");

        if(specificationDto.getDriveType() == null)
            specificationDto.setDriveType("");

        if(specificationDto.getConsumption() == null)
            specificationDto.setConsumption(0L);

        if(specificationDto.getAcType() == null)
            specificationDto.setAcType("");

        if(specificationDto.getSeatCount() == null)
            specificationDto.setSeatCount(0);

        if(specificationDto.getColor() == null)
            specificationDto.setColor("");

        if(specificationDto.getAdditionalEquipment() == null)
            specificationDto.setAdditionalEquipment(0);

        if(specificationDto.getExtraFeatures() == null)
            specificationDto.setExtraFeatures(0);
    }

    public static ArrayList<String> checkboxToStringList(int dec, String inputName){
        ArrayList<String> extraFeaturesList = new ArrayList<>();
        ArrayList<String> additionalEquipmentList = new ArrayList<>();
        ArrayList<Character> binaryList = new ArrayList<>();
        ArrayList<String> checkedContent = new ArrayList<>();

        String binary = Integer.toBinaryString(dec);

        if(inputName.equals("additionalEquipment")){
            additionalEquipmentList.add("Fog Lights");
            additionalEquipmentList.add("Steering Lights");
            additionalEquipmentList.add("Aluminium Rims");
            additionalEquipmentList.add("Lane Assist");
            additionalEquipmentList.add("Speed Limiter");
            additionalEquipmentList.add("Infotainment Screen");
            additionalEquipmentList.add("LED Headlights");
            additionalEquipmentList.add("Navigation");
            additionalEquipmentList.add("ABS");
            additionalEquipmentList.add("Cruise Control");
            additionalEquipmentList.add("Rain Sensor");
            additionalEquipmentList.add("Rear Windshield Wiper");

            binary = addZeros(binary, additionalEquipmentList.size());
        }

        else if(inputName.equals("extraFeatures")){
            extraFeaturesList.add("Tyre Pressure Control");
            extraFeaturesList.add("Start/Stop System");
            extraFeaturesList.add("Central Armrest");
            extraFeaturesList.add("Apple CarPlay");
            extraFeaturesList.add("Android Auto");
            extraFeaturesList.add("Bluetooth");
            extraFeaturesList.add("Foldable Rear Seats");
            extraFeaturesList.add("Steering Wheel Adjustment");
            extraFeaturesList.add("Side Mirror Heating");
            extraFeaturesList.add("Electrical Side Mirror Adjustment");
            extraFeaturesList.add("Electrical Seat Adjustment");
            extraFeaturesList.add("Electrical Window Raising");

            binary = addZeros(binary, extraFeaturesList.size());
        }

        for(Character c: binary.toCharArray()){
            binaryList.add(c);
        }

        if(inputName.equals("additionalEquipment")) {
            for (int i = 0; i < additionalEquipmentList.size(); i++) {
                if(binaryList.get(i) == '1')
                    checkedContent.add(additionalEquipmentList.get(i));
            }
        }
        if(inputName.equals("extraFeatures")) {
            for (int i = 0; i < extraFeaturesList.size(); i++) {
                if(binaryList.get(i) == '1')
                    checkedContent.add(extraFeaturesList.get(i));
            }
        }

        return checkedContent;
    }

    private static String addZeros(String binaryInput, int neededLength){
        StringBuilder finalBinary = new StringBuilder(binaryInput);
        while(finalBinary.length() < neededLength)
            finalBinary.insert(0, "0");
        return finalBinary.toString();
    }

    public void publishSpecification(Specification specs){
        specificationRepository.save(specs);
    }
    public Specification findByListingId(String listingId){return specificationRepository.findById(listingId);}
}