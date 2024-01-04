package hr.carpazar.controllers;

import hr.carpazar.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingController {
    @Autowired
    private ListingService listingService;

    @GetMapping(path="/add-listing")
    public String openListingForm(){
        return "new_listing";
    }

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}