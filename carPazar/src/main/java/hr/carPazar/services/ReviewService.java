package hr.carPazar.services;

import hr.carPazar.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

}