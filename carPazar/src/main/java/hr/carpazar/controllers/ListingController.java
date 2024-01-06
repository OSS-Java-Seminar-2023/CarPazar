package hr.carpazar.controllers;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.services.ListingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ListingController {
    @Autowired
    private ListingService listingService;

    @GetMapping(path="/add-listing")
    public String openListingForm(){
        return "new_listing";
    }


    // kod nece radit ako u sesiji ne postoji user (zabranit pokusaj stvaranja listinga triba ako nije logged in)
    @PostMapping(path="/add-listing")
    public String newListing(@ModelAttribute ListingDto listingDto, @RequestParam("images")List<MultipartFile> imageList, HttpSession httpSession){
        String userid = httpSession.getAttribute("user_id").toString();

        Listing listing = listingService.createListingFromDto(listingDto, userid);

        listingService.publishListing(listing);

        String directory = "C:/CarPazar/listings/";
        String listingUUID = listing.getId();
        directory += listingUUID + "/";

        ListingService.createDirectory(directory);

        int imageCounter = 1;

        for(MultipartFile image: imageList){
            String fileExtension = ListingService.getFileExtension(image.getOriginalFilename());
            String newFilename = "img_" + imageCounter + fileExtension;

            imageCounter++;

            try {
                image.transferTo(new File(directory + newFilename));
            } catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }
        }

        listingService.updateImgDirectory(directory, listingUUID);

        return "redirect:/add-info";
    }

    @GetMapping(path="/add-info")
    public String openSpecificationForm(){
        return "new_listing_info";
    }

    @PostMapping(path = "/add-info")
    public String addSpecifications(@ModelAttribute int x){  // int x zaminit sa SpecificationDto kad se napravi
        return "redirect:/home"; // redirect:/listings/<uuid> za pregled objavljenog oglasa
    }

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}