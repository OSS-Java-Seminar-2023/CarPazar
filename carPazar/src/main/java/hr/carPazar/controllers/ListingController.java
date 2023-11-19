package hr.carPazar.controllers;

import hr.carPazar.repositories.ListingService.java
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ListingController {
    @Autowired
    private ListingService listingService;
}