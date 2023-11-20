package hr.carPazar.controllers;

import hr.carPazar.services.ListingService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ListingController {
    @Autowired
    private ListingService listingService;

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}