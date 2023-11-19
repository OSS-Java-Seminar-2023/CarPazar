package hr.carPazar.repositories;
import hr.carPazar.repositories.UserRepository.java;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

}