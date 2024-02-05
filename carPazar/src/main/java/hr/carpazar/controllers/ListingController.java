package hr.carpazar.controllers;

import static hr.carpazar.services.SpecificationService.checkboxToStringList;

import hr.carpazar.dtos.FilterDto;
import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.*;
import hr.carpazar.repositories.MessageRepository;
import hr.carpazar.services.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private FilterService filterService;


    @GetMapping(path = "/add-listing")
    public String openListingForm(HttpSession httpSession, Model model) {
        String userid = (String) httpSession.getAttribute("user_id");
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        return "new_listing";
    }

    @PostMapping(path = "/add-listing")
    public String newListing(@ModelAttribute ListingDto listingDto,
                             @RequestParam("images") List<MultipartFile> imageList, HttpSession httpSession, Model model) {
        String userid = (String) httpSession.getAttribute("user_id");
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        Listing listing = listingService.createListingFromDto(listingDto, userid);

        User user = userService.findById(userid);
        if (user.getIsPremium())
            listing.setIsSponsored(Boolean.TRUE);

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
    public ResponseEntity<byte[]> getListingImage(@PathVariable String listingId, Model model) throws IOException {
        Path imgPath = Paths.get(listingService.getDirPath(listingId) + "/img_1.png");
        byte[] imgBytes = Files.readAllBytes(imgPath);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imgBytes);
    }
    @GetMapping(path = "/listingWithImages/{listingId}")
    public String getListingWithImages(@PathVariable String listingId, Model model,HttpSession session) {
        String loggedInId = session.getAttribute("user_id") != null ? session.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        model.addAttribute("userID", loggedInId);
        List<String> fileNames = listingService.getImageFilenames(listingId);
        Listing listing = listingService.findById(listingId);
        Specification specification = specificationService.findByListingId(listingId);
        model.addAttribute("listing", listing);
        model.addAttribute("fileNames", fileNames);
        model.addAttribute("specification", specification);
        ArrayList<String> exFeat = checkboxToStringList(specification.getExtraFeatures(), "extraFeatures", true);
        model.addAttribute("exFeat", exFeat);
        ArrayList<String> addEquip =
                checkboxToStringList(specification.getAdditionalEquipment(), "additionalEquipment", true);
        model.addAttribute("addEquip", addEquip);
        return "listingView";
    }

    @GetMapping(path = "/imagesListing/{listingId}/{imageIndex}")
    public ResponseEntity<byte[]> getListingImage(@PathVariable String listingId, @PathVariable String imageIndex)
            throws IOException {
        Path imgPath = Paths.get("C:/CarPazar/listings/" + listingId + "/" + imageIndex);
        if (Files.exists(imgPath)) {
            byte[] imgBytes = Files.readAllBytes(imgPath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imgBytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model, HttpSession httpSession) {
        String loggedInId =
                httpSession.getAttribute("user_id") != null ? httpSession.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        String[] keywords = query.trim().split("\\s+");
        List<Listing> searchResults = listingService.search(keywords);
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("userID", loggedInId);
        return "/search";
    }

    @GetMapping(path = "/listings")
    public String openAllListingsForm(@RequestParam(name = "sort", defaultValue = "dateDesc") String sort,
                                      @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model,
                                      HttpSession httpSession) {
        String loggedInId =
                httpSession.getAttribute("user_id") != null ? httpSession.getAttribute("user_id").toString() : null;
        model.addAttribute("userID", loggedInId);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

        List<Listing> sortedListings = listingService.getSortedListings(sort);
        List<Listing> allListings = listingService.isolatePremiumListings(sortedListings);
        Page<Listing> listingPage = listingService.findPaginated(allListings, pageable);

        model.addAttribute("sortedListings", sortedListings);
        model.addAttribute("listingPage", listingPage);

        int totalPages = listingPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "allListings";
    }

    @PostMapping(path = "/listings")
    public String openFilteredListings(@ModelAttribute FilterDto filterDto,
                                       @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model,
                                       HttpSession httpSession) {
        Filter filters = filterService.setDefaults(filterDto);
        List<Specification> specs = filterService.findSpecificationByFilter(filters);

        List<Listing> sortedListings = listingService.getSortedListings(filters.getSort());

        List<Listing> allListings = listingService.isolatePremiumListings(sortedListings);

        List<Listing> listingsWithSpecs = allListings.stream()
                .filter(listing -> specs.stream()
                        .anyMatch(specification -> specification.getId().equals(listing.getId())))
                .collect(Collectors.toList());

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Listing> listingPage = listingService.findPaginated(listingsWithSpecs, pageable);

        model.addAttribute("listingPage", listingPage);
        model.addAttribute("listingsWithSpecs", listingsWithSpecs);
        int totalPages = listingPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "allListings";
    }

    @Transactional
    @PostMapping("/deleteListing/{id}")
    public String deleteListing(@PathVariable String id, HttpSession session, Model model) {
        String loggedInId = (String) session.getAttribute("user_id");
        String loggedInUsername = (String) session.getAttribute("user_username");
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        User user = userService.findByUserName(loggedInUsername);

        Optional<Listing> listingOptional = Optional.ofNullable(listingService.findById(id));

        if (!user.getIsAdmin() && !listingOptional.get().getUserId().equals(user)) {
            return "redirect:/notFound";
        }

        Listing listing = listingOptional.get();

        List<Chat> chats = chatService.findAllChatsByListing(listing);

        chats.forEach(chat -> {
            messageService.deleteByChatId(chat);
            chatService.deleteById(chat.getId());
        });

        listingService.deleteListing(listing);

        return "redirect:/adminPanel";
    }

    @GetMapping(path = "/listing/{listingId}")
    public String viewListing(@PathVariable String listingId, Model model, HttpSession httpSession) {
        String userid = (String) httpSession.getAttribute("user_id");
        if (userid == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Listing listing = listingService.findById(listingId);
        Specification specification = specificationService.findByListingId(listingId);
        model.addAttribute("listing", listing);
        model.addAttribute("specification", specification);
        return "listingView";
    }

    @GetMapping(path = "/editListing/{listingId}")
    public String editListing(@PathVariable String listingId, Model model, HttpSession httpSession) {
        String loggedInId =
                (httpSession.getAttribute("user_id") != null) ? httpSession.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<Listing> listingOptional = Optional.ofNullable(listingService.findById(listingId));
        if (listingOptional.isPresent()) {
            Listing listing = listingOptional.get();
            ListingDto listingDTO = new ListingDto();
            listingDTO.setPrice(listing.getPrice());
            listingDTO.setDescription(listing.getDescription());
            listingDTO.setTitle(listing.getTitle());
            listingDTO.setId(listing.getId());
            listingDTO.setIsSold(listing.getIsSold());

            User user = userService.findById(loggedInId);
            if (!user.getIsAdmin() && !(listing.getUserId() == user)) {
                return "redirect:/notFound";
            }

            model.addAttribute("listingDTO", listingDTO);
        } else {
            return "redirect:/notFound";
        }
        return "editListing";
    }

    @GetMapping(path = "/markAsSold/{listingId}")
    public String soldSwitch(@PathVariable String listingId, Model model, HttpSession httpSession) {
        String loggedInId =
                (httpSession.getAttribute("user_id") != null) ? httpSession.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }
        Optional<Listing> listingOptional = Optional.ofNullable(listingService.findById(listingId));
        if (listingOptional.isPresent()) {
            User user = userService.findById(loggedInId);
            Listing listing = listingOptional.get();

            if (!user.getIsAdmin() && !(listing.getUserId() == user)) {
                return "redirect:/notFound";
            }
            else{
                listingService.soldSwitch(listing);
                return "redirect:/home";
            }
        }
        else
            return "redirect:/notFound";

    }

    @PostMapping(path = "/editListing/update")
    public String updateListing(@ModelAttribute ListingDto listingDTO, HttpSession session, Model model)
            throws ParseException {
        String loggedInId =
                (session.getAttribute("user_id") != null) ? session.getAttribute("user_id").toString() : null;
        if (loggedInId == null) {
            model.addAttribute("not_logged_in", "You have to log in in order to access this site!");
            return "notFound";
        }

        String listingID = listingDTO.getId();
        Optional<Listing> listingOptional = Optional.ofNullable(listingService.findById(listingID));

        if (!listingOptional.isPresent()) {
            return "redirect:/notFound";
        }

        Listing listing = listingOptional.get();
        listing.setTitle(listingDTO.getTitle());
        listing.setDescription(listingDTO.getDescription());
        listing.setPrice(listingDTO.getPrice());

        User user = userService.findById(loggedInId);
        if (!user.getIsAdmin() && !(listing.getUserId() == user)) {
            return "redirect:/notFound";
        }

        listingService.saveListing(listing);

        return "redirect:/mylistings";
    }

    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }
}