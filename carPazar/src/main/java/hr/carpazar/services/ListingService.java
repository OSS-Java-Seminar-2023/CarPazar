package hr.carpazar.services;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.models.Specification;
import hr.carpazar.repositories.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


@Service
public class ListingService {

    public static Listing createListingFromDto(ListingDto listingDto){
        Map<String, String> postParams = new Hashtable<>();
        postParams.put("localDateTime", listingDto.getLocalDateTime().toString());
        postParams.put("title", listingDto.getTitle());
        postParams.put("description", listingDto.getDescription());
        postParams.put("price", listingDto.getPrice().toString());
        postParams.put("isSponsored", listingDto.getIsSponsored().toString());
        postParams.put("isSold", listingDto.getIsSold().toString());
        postParams.put("specification", listingDto.getSpecifications().toString());

        return createListing(postParams);
    }

    public static Listing createListing(Map<String, String> listingData){
        Listing listing = new Listing();
        listing.setListingDatetime(LocalDateTime.parse(listingData.get("localDateTime")));
        listing.setTitle(listingData.get("title"));
        listing.setDescription(listingData.get("description"));
        listing.setPrice(Long.parseLong(listingData.get("price")));
        listing.setIsSponsored(Boolean.parseBoolean(listingData.get("isSponsored")));
        listing.setIsSold(Boolean.parseBoolean(listingData.get("isSold")));
        // set specs ???
        return listing;
    }

    @Autowired
    private ListingRepository listingRepository;
    public List<Listing> findByUserId(String userId){return listingRepository.findByUserId_Id(userId);}

}