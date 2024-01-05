package hr.carpazar.controllers;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ListingController {
    @Autowired
    private ListingService listingService;

    @GetMapping(path="/add-listing")
    public String openListingForm(){
        return "new_listing";
    }

    @PostMapping(path="/add-listing")
    public String newListing(@ModelAttribute ListingDto listingDto){
        Listing listing = ListingService.createListingFromDto(listingDto);


        return "home";
    }

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}