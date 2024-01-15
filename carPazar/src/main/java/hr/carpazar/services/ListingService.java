package hr.carpazar.services;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.models.User;
import hr.carpazar.repositories.ListingRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


@Service
public class ListingService {
    @Autowired
    private ListingRepository listingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource msgSource;



    public String getDirPath(String listingUUID){
        return msgSource.getMessage("file.directory", null, null) + listingUUID + "/";
    }

    public User findSellerByListingID(String listingID) {
        Listing listing= listingRepository.findById(listingID).get(0);
        return listing.getUserId();
    }

    public Listing createListingFromDto(ListingDto listingDto, String userid){
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

    public Listing createListing(Map<String, String> listingData){
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


    public void publishListing(Listing listing){
        listingRepository.save(listing);
    }

    public void deleteListing(Listing listing){
        listingRepository.delete(listing);
    }

    public static String getFileExtension(String filename){
        String filenameParts[] = filename.split("\\.");
        String fileExtension =  "." + filenameParts[filenameParts.length - 1];
        return fileExtension;
    }

    public static void createDirectory(String directory){
        Path path = Paths.get(directory);

        if(!Files.exists(path)){
            try{
                Files.createDirectories(path);
            } catch (IOException ioException){
                System.out.println(ioException.getMessage());
            }
        }
        else{
            System.out.println("Already exists.");
        }
    }

    public void updateImgDirectory(String directory, String listingUUID){
        Listing listing = findById(listingUUID);
        listing.setImgDirectory(directory);
        listingRepository.save(listing);
    }
    public List<String> getImageFilenames(String listingId) {
        List<String> filenames = new ArrayList<>();
        File folder = new File("C:/CarPazar/listings/" + listingId+"/");
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filenames.add(file.getName());
                }
            }
        }
        for(String name:filenames){
            System.out.println(name);
        }
        return filenames;
    }
    public Listing findById(String id){
        return listingRepository.findById(id).get(0);
    }


    public List<Listing> findByUserId(String userId){
        return listingRepository.findByUserId_Id(userId);
    }
    public List<Listing> getAll(){return listingRepository.findAll();}
}