package hr.carpazar.services;

import hr.carpazar.repositories.SpecificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository specificationRepository;

}