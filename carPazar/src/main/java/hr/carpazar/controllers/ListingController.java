package hr.carpazar.controllers;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.services.ImageService;
import hr.carpazar.services.ListingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            ImageService.saveAsPng(image, directory, imageCounter);
            imageCounter++;
        }

        listingService.updateImgDirectory(directory, listingUUID);
        httpSession.setAttribute("listing_id", listingUUID);

        return "redirect:/add-info";
    }
    @GetMapping(path = "/imagesListing/{listingId}")
    public ResponseEntity<byte[]> getListingImage(@PathVariable String listingId) throws IOException {
        Path imgPath = Paths.get("C:/CarPazar/listings/"+listingId+"/img_1.png");
        byte[] imgBytes = Files.readAllBytes(imgPath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imgBytes);

    }


    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}