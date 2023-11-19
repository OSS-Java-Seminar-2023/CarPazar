package hr.carPazar.repositories;
import hr.carPazar.repositories.SpecificationRepository.java;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class SpecificationService {

    @Autowired
    private SpecificationRepository specificationRepository;

}