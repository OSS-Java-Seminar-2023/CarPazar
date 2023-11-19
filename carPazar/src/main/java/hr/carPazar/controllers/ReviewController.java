package hr.carPazar.controllers;

import hr.carPazar.repositories.ReviewService.java

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
}