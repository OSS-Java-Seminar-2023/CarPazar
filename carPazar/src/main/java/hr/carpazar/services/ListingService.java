package hr.carpazar.services;

import hr.carpazar.repositories.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

}