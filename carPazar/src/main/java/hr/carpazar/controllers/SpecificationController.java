package hr.carpazar.controllers;

import hr.carpazar.dtos.SpecificationDto;
import hr.carpazar.models.Specification;
import hr.carpazar.services.SpecificationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping(path="/add-info")
    public String openSpecificationForm(){
        return "new_listing_info";
    }

    @PostMapping(path = "/add-info")
    public String addListingSpecs(@ModelAttribute SpecificationDto specificationDto, HttpSession httpSession){
        String listingid = httpSession.getAttribute("listing_id").toString();

        Specification specs = specificationService.createSpecificationFromDto(specificationDto, listingid);
        specificationService.publishSpecification(specs);

        return "redirect:/listing/" + listingid;
    }

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }
}