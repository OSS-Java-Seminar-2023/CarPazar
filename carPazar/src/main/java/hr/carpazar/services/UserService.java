package hr.carpazar.services;

import hr.carpazar.models.User;
import hr.carpazar.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.sql.Date;


@NoArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static User createUser(Map<String, String> userData){
        User user = User.builder().
                fullName(userData.get("firstName") + " " + userData.get("surname")).
                birthDate(Date.valueOf(userData.get("birthDate"))).
                phoneNumber(userData.get("phoneNumber")).
                userName(userData.get("username")).
                email(userData.get("email")).
                hashedPassword(userData.get("password")).build();

        return user;
    }

    public void registerUser(User user){
        userRepository.save(user);
    }

}