package hr.carPazar.repositories;

import hr.carPazar.repositories.ReviewRepository.java;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

}