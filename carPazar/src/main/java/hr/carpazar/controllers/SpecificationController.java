package hr.carpazar.controllers;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.dtos.SpecificationDto;
import hr.carpazar.models.Listing;
import hr.carpazar.models.Specification;
import hr.carpazar.services.ListingService;
import hr.carpazar.services.SpecificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private ListingService listingService;

    @GetMapping(path="/add-info")
    public String openSpecificationForm(){
        return "new_listing_info";
    }

    @PostMapping(path = "/add-info")
    public String addListingSpecs(@ModelAttribute SpecificationDto specificationDto, HttpSession httpSession,Model model){
        String userid = httpSession.getAttribute("user_id").toString();
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        String listingid = httpSession.getAttribute("listing_id").toString();

        Specification specs = specificationService.createSpecificationFromDto(specificationDto, listingid);
        specificationService.publishSpecification(specs);

        return "redirect:/listingWithImages/" + listingid;
    }

    @GetMapping(path="/editSpecification/{listingId}")
    public String editSpecification(@PathVariable String listingId, Model model,HttpSession httpSession){
        String loggedInId = (httpSession.getAttribute("user_id") != null) ? httpSession.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<Specification> specificationOptional = Optional.ofNullable(specificationService.findByListingId(listingId));
        if (specificationOptional.isPresent()) {
            Specification specification=specificationOptional.get();
            SpecificationDto specificationDto = new SpecificationDto();
            specificationDto.setConsumption(specification.getConsumption());
            specificationDto.setId(specification.getId());
            specificationDto.setBrand(specification.getBrand());
            specificationDto.setModel(specification.getModel());
            specificationDto.setEngineType(specification.getEngineType());
            specificationDto.setShifterType(specification.getShifterType());
            specificationDto.setKilometersTravelled(specification.getKilometersTravelled());
            specificationDto.setManufactureYear(specification.getManufactureYear());
            specificationDto.setInTrafficSince(specification.getInTrafficSince());
            specificationDto.setDoorCount(specification.getDoorCount());
            specificationDto.setGearCount(specification.getGearCount());
            specificationDto.setLocation(specification.getLocation());
            specificationDto.setBodyShape(specification.getBodyShape());
            specificationDto.setIsUsed(specification.getIsUsed());
            specificationDto.setDriveType(specification.getDriveType());
            specificationDto.setConsumption(specification.getConsumption());
            specificationDto.setAcType(specification.getAcType());
            specificationDto.setSeatCount(specification.getSeatCount());
            specificationDto.setRegistrationUntil(specification.getRegistrationUntil());
            specificationDto.setOwnerNo(specification.getOwnerNo());
            specificationDto.setColor(specification.getColor());
            specificationDto.setAdditionalEquipment(specification.getAdditionalEquipment());
            specificationDto.setExtraFeatures(specification.getExtraFeatures());

            model.addAttribute("specificationDto", specificationDto);
        } else {
            return "redirect:/notFound";
        }
        return "editSpecification";
    }

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }
}