package hr.carpazar.controllers;

import hr.carpazar.services.SpecificationService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    public SpecificationController(SpecificationService specificationService) {
        this.specificationService = specificationService;
    }
}