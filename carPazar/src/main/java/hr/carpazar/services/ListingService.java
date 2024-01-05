package hr.carpazar.services;

import hr.carpazar.dtos.ListingDto;
import hr.carpazar.models.Listing;
import hr.carpazar.repositories.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Hashtable;
import java.util.Map;


@Service
public class ListingService {

    public static Listing createListingFromDto(ListingDto listingDto){
        Map<String, String> postParams = new Hashtable<>();
        return null;
    }

    @Autowired
    private ListingRepository listingRepository;

}