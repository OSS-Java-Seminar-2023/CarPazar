package hr.carpazar.controllers;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.*;
import hr.carpazar.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hr.carpazar.services.SpecificationService.checkboxToStringList;

@Controller
public class ListingController {
    @Autowired
    private ListingService listingService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;


    @GetMapping(path="/add-listing")
    public String openListingForm(HttpSession httpSession,Model model){
        String userid = (String) httpSession.getAttribute("user_id");
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        return "new_listing";
    }


    // kod nece radit ako u sesiji ne postoji user (zabranit pokusaj stvaranja listinga triba ako nije logged in)
    @PostMapping(path="/add-listing")
    public String newListing(@ModelAttribute ListingDto listingDto, @RequestParam("images")List<MultipartFile> imageList, HttpSession httpSession, Model model){
        String userid = httpSession.getAttribute("user_id").toString();
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        Listing listing = listingService.createListingFromDto(listingDto, userid);

        listingService.publishListing(listing);
        String listingUUID = listing.getId();
        String directoryPath = listingService.getDirPath(listingUUID);

        ListingService.createDirectory(directoryPath);
        ImageService.saveAsPng(imageList, directoryPath);
        listingService.updateImgDirectory(directoryPath, listingUUID);

        httpSession.setAttribute("listing_id", listingUUID);

        return "redirect:/add-info";
    }
    @GetMapping(path = "/imagesListing/{listingId}")
    public ResponseEntity<byte[]> getListingImage(@PathVariable String listingId,Model model) throws IOException {
        Path imgPath = Paths.get(listingService.getDirPath(listingId) + "/img_1.png");
        byte[] imgBytes = Files.readAllBytes(imgPath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(imgBytes);

    }
    @GetMapping(path = "/listingWithImages/{listingId}")
    public String getListingWithImages(@PathVariable String listingId, Model model) {
        List<String> fileNames = listingService.getImageFilenames(listingId);
        Listing listing = listingService.findById(listingId);
        Specification specification = specificationService.findByListingId(listingId);
        model.addAttribute("listing", listing);
        model.addAttribute("fileNames", fileNames);
        model.addAttribute("specification", specification);
        ArrayList<String> exFeat = checkboxToStringList(specification.getExtraFeatures(),"extraFeatures");
        model.addAttribute("exFeat",exFeat);
        ArrayList<String> addEquip = checkboxToStringList(specification.getAdditionalEquipment(),"additionalEquipment");
        model.addAttribute("addEquip",addEquip);
        return "listingView";
    }

    @GetMapping(path = "/imagesListing/{listingId}/{imageIndex}")
    public ResponseEntity<byte[]> getListingImage(@PathVariable String listingId, @PathVariable String imageIndex) throws IOException {
        Path imgPath = Paths.get("C:/CarPazar/listings/" + listingId + "/" + imageIndex);
        if (Files.exists(imgPath)) {
            byte[] imgBytes = Files.readAllBytes(imgPath);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imgBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model, HttpSession httpSession) {
        String userid = httpSession.getAttribute("user_id").toString();
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        String[] keywords = query.trim().split("\\s+");
        List<Listing> searchResults = listingService.search(keywords);
        model.addAttribute("searchResults", searchResults);
        return "/search";
    }

    @GetMapping(path= "/listings")
    public String openAllListingsForm(Model model, HttpSession httpSession)
    {
     /*   String userid = httpSession.getAttribute("user_id").toString();
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }*/
        List<Listing> listings = listingService.getAll();
        model.addAttribute("listings",listings);
        return "allListings";
    }

    @Transactional
    @PostMapping("/deleteListing/{id}")
    public String deleteListing(@PathVariable String id, HttpSession session,Model model) {
        String loggedInId = session.getAttribute("user_id").toString();
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        User user = userService.findByUserName(loggedInUsername);

        Optional<Listing> listingOptional = Optional.ofNullable(listingService.findById(id));

        if(!user.getIsAdmin() && !listingOptional.get().getUserId().equals(user)){
            return "redirect:/notFound";
        }

        Listing listing = listingOptional.get();

        List<Chat> chats = chatService.findAllChatsByListing(listing);
        for (Chat chat : chats) {
            messageService.deleteByChatId(chat);
        }

        chatService.deleteByListingId(listing);
        listingService.deleteListing(listing);

        return "redirect:/adminPanel";
    }

    @GetMapping(path="/listing/{listingId}")
    public String viewListing(@PathVariable String listingId, Model model,HttpSession httpSession){
        String userid = httpSession.getAttribute("user_id").toString();
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Listing listing = listingService.findById(listingId);
        Specification specification = specificationService.findByListingId(listingId);
        model.addAttribute("listing",listing);
        model.addAttribute("specification",specification);
        return "listingView";
    }




    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}