package hr.carPazar.controllers;

import hr.carPazar.repositories.SpecificationService.java

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;
}