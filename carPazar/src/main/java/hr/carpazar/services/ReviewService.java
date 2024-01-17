package hr.carpazar.services;

import hr.carpazar.models.User;
import hr.carpazar.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void deleteByUser(User user){reviewRepository.deleteByUser(user);};

}