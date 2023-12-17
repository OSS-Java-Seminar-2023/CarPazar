package hr.carpazar.services;

import hr.carpazar.Dtos.UserDto;
import hr.carpazar.models.User;
import hr.carpazar.repositories.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.sql.Date;


@NoArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public static User createUser(Map<String, String> userData){
        User user = new User();
        user.setFullName(userData.get("firstName") + " " + userData.get("surname"));
        user.setBirthDate(Date.valueOf(userData.get("birthDate")));
        user.setPhoneNumber(userData.get("phoneNumber"));
        user.setUserName(userData.get("username"));
        user.setEmail(userData.get("email"));
        user.setHashedPassword(userData.get("password"));
        return user;
    }
    public static User createUserFromDto(UserDto userDto) {
        Map<String, String> postParams = new Hashtable<>();
        postParams.put("firstName", userDto.getFirstName());
        postParams.put("surname", userDto.getSurname());
        postParams.put("birthDate", userDto.getBirthDate());
        if (userDto.getPhoneNumber() != null) {
            postParams.put("phoneNumber", userDto.getPhoneNumber().toString());
        } else {
            postParams.put("phoneNumber", "");
        }
        postParams.put("email", userDto.getEmail());
        postParams.put("username", userDto.getUsername());
        postParams.put("password", HashService.generateSHA512(userDto.getPassword()));

        return createUser(postParams);
    }



    public void registerUser(User user){
        userRepository.save(user);
    }

    public Boolean isExistingCheck(User user){
        return !(userRepository.countByUserName(user.getUserName()) == 0 &&
                userRepository.countByEmail(user.getEmail()) == 0 &&
                userRepository.countById(user.getId()) == 0);
    }
    public User fetchUserByUsername(String username){
        return userRepository.findByUserName(username).get(0);
    }

    public String preparePasswordComparing(String usernameFieldText, String passwordFieldText) {
        User dbUser;
        if (usernameFieldText.contains("@")) {
            dbUser = fetchUserByEmail(usernameFieldText);
        } else {
            dbUser = fetchUserByUsername(usernameFieldText);
        }

        if (dbUser != null) {
            return dbUser.getHashedPassword();
        } else {
            return null;
        }
    }

    public User fetchUserByEmail(String email){
        return userRepository.findByEmail(email).get(0);
    }

}