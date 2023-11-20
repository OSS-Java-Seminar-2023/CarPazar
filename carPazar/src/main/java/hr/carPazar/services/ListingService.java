package hr.carPazar.services;

import hr.carPazar.repositories.ListingRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

}