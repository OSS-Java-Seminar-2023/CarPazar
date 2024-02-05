package hr.carpazar.services;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.repositories.ListingRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource msgSource;

    public String getDirPath(String listingUUID) {
        return msgSource.getMessage("file.directory", null, null) + listingUUID + "/";
    }

    public User findSellerByListingID(String listingID) {
        Listing listing = listingRepository.findById(listingID).get(0);
        return listing.getUserId();
    }

    public Listing createListingFromDto(ListingDto listingDto, String userid) {
        Map<String, String> postParams = new Hashtable<>();
        postParams.put("localDateTime", LocalDateTime.now().toString());
        postParams.put("title", listingDto.getTitle());
        postParams.put("description", listingDto.getDescription());
        postParams.put("price", listingDto.getPrice().toString());
        postParams.put("isSponsored", Boolean.FALSE.toString());
        postParams.put("isSold", Boolean.FALSE.toString());
        postParams.put("userid", userid);

        return createListing(postParams);
    }

    public Listing createListing(Map<String, String> listingData) {
        Listing listing = new Listing();
        User publisher = userService.findById(listingData.get("userid"));

        listing.setListingDatetime(LocalDateTime.parse(listingData.get("localDateTime")));
        listing.setTitle(listingData.get("title"));
        listing.setDescription(listingData.get("description"));
        listing.setPrice(Long.parseLong(listingData.get("price")));
        listing.setIsSponsored(Boolean.parseBoolean(listingData.get("isSponsored")));
        listing.setIsSold(Boolean.parseBoolean(listingData.get("isSold")));
        listing.setUserId(publisher);

        return listing;
    }

    public void publishListing(Listing listing) {
        listingRepository.save(listing);
    }

    public void deleteListing(Listing listing) {
        listingRepository.delete(listing);
    }


    public void soldSwitch(Listing listing){
        if(listing.getIsSold())
            listing.setIsSold(false);
        else
            listing.setIsSold(true);

        publishListing(listing);
    }
    public static void createDirectory(String directory) {
        Path path = Paths.get(directory);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    public void updateImgDirectory(String directory, String listingUUID) {
        Listing listing = findById(listingUUID);
        listing.setImgDirectory(directory);
        listingRepository.save(listing);
    }
    public List<String> getImageFilenames(String listingId) {
        List<String> filenames = new ArrayList<>();
        File folder = new File("C:/CarPazar/listings/" + listingId + "/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filenames.add(file.getName());
                }
            }
        }
        return filenames;
    }

    public List<Listing> search(String[] keywords) {
        List<Listing> allListings = listingRepository.findAll();

        Map<Listing, Integer> listingMatches = allListings.stream()
                .collect(Collectors.toMap(
                        listing -> listing,
                        listing -> (int) Arrays.stream(keywords)
                                .filter(word -> listing.getTitle().toLowerCase().contains(word.toLowerCase()))
                                .count()
                ));

        List<Listing> searchResults;
        searchResults = listingMatches.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return searchResults;
    }

    public Page findPaginated(List<Listing> sortedListings, PageRequest pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Listing> list;

        if (sortedListings.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, sortedListings.size());
            list = sortedListings.subList(startItem, toIndex);
        }

        Page<Listing> listingPage = new PageImpl<Listing>(list, pageable, sortedListings.size());

        return listingPage;
    }

    public Page findPaginatedByUser(PageRequest pageable, String loggedInId) {
        List<Listing> allListings = findByUserId(loggedInId);
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Listing> list;

        if (allListings.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allListings.size());
            list = allListings.subList(startItem, toIndex);
        }

        Page<Listing> listingPage =
                new PageImpl<Listing>(list, PageRequest.of(currentPage, pageSize), allListings.size());

        return listingPage;
    }
    public Listing findById(String id) {
        return listingRepository.findById(id).get(0);
    }

    public void saveListing(Listing listing) {
        listingRepository.save(listing);
    }

    public List<Listing> findByUserId(String userId) {
        return listingRepository.findByUserId_Id(userId);
    }
    public List<Listing> getAll() {
        return listingRepository.findAll();
    }
    private List<Listing> getAllByPriceDesc() {
        return listingRepository.findAllByOrderByPriceDesc();
    }
    private List<Listing> getAllByPriceAsc() {
        return listingRepository.findAllByOrderByPriceAsc();
    }
    private List<Listing> getAllByDateDesc() {
        return listingRepository.findAllByOrderByListingDatetimeDesc();
    }
    private List<Listing> getAllByDateAsc() {
        return listingRepository.findAllByOrderByListingDatetimeAsc();
    }

    public List<Listing> getSortedListings(String sort) {
        return switch (sort) {
            case "priceDesc" -> getAllByPriceDesc();
            case "priceAsc" -> getAllByPriceAsc();
            case "dateDesc" -> getAllByDateDesc();
            case "dateAsc" -> getAllByDateAsc();
            default -> getAll();
        };
    }

    public List<Listing> isolatePremiumListings(List<Listing> sortedListings){
        List<Listing> sortedPremiumList = new ArrayList<>();

        for(Listing listing: sortedListings){
            if(listing.getIsSponsored()) {
                sortedPremiumList.add(listing);
            }
        }

        for(Listing listing: sortedListings){
            if(!listing.getIsSponsored()) {
                sortedPremiumList.add(listing);
            }
        }

        return sortedPremiumList;
    }

}