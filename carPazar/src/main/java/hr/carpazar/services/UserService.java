package hr.carpazar.services;

import hr.carpazar.models.User;
import hr.carpazar.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

}