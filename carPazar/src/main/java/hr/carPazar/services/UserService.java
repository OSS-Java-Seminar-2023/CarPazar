package hr.carPazar.services;

import hr.carPazar.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

}