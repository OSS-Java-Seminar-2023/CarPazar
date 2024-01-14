package hr.carpazar.controllers;

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
    public String addListingSpecs(@ModelAttribute SpecificationDto specificationDto, HttpSession httpSession){
        String listingid = httpSession.getAttribute("listing_id").toString();

        Specification specs = specificationService.createSpecificationFromDto(specificationDto, listingid);
        specificationService.publishSpecification(specs);

        return "redirect:/listingWithImages/" + listingid;
    }
    @GetMapping(path="/listing/{listingId}")
    public String viewListing(@PathVariable String listingId, Model model){
        Listing listing = listingService.findById(listingId);
        Specification specification = specificationService.findByListingId(listingId);
        model.addAttribute("listing",listing);
        model.addAttribute("specification",specification);
        return "listingView";
    }



    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }
}