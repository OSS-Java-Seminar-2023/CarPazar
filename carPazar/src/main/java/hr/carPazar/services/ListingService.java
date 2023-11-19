package hr.carPazar.repositories;

import hr.carPazar.repositories.ListingRepository.java;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ListingService {

    @Autowired
    private ListingRepository listingRepository;

}