package hr.carpazar.serviceTests;

import hr.carpazar.dtos.UserDto;
import hr.carpazar.models.User;
import hr.carpazar.repositories.UserRepository;
import hr.carpazar.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUser(){
        Map<String, String> userData = new HashMap<>();
        userData.put("firstName","Jo");
        userData.put("surname","Mama");
        userData.put("birthDate","1999-01-01");
        userData.put("username","JoMama");
        userData.put("phoneNumber", "123456789");
        userData.put("email", "jo.mama@example.com");
        userData.put("password", "hashedPassword");

        User result = UserService.createUser(userData);

        assertEquals("Jo Mama", result.getFullName());
        assertEquals(Date.valueOf("1999-01-01"), result.getBirthDate());
        assertEquals("JoMama", result.getUserName());
        assertEquals("123456789", result.getPhoneNumber());
        assertEquals("jo.mama@example.com", result.getEmail());
        assertEquals("hashedPassword", result.getHashedPassword());
    }

    @Test
    void testCreateUserFromDto(){

        UserDto userDto = new UserDto();
        userDto.setFirstName("Ivan");
        userDto.setSurname("Testic");
        userDto.setBirthDate("1995-02-15");
        userDto.setPhoneNumber(987654321);
        userDto.setEmail("ivan.testic@example.com");
        userDto.setUsername("ivantestic");
        userDto.setPassword("password");

        User result = UserService.createUserFromDto(userDto);

        assertEquals("Ivan Testic", result.getFullName());
        assertEquals(Date.valueOf("1995-02-15"), result.getBirthDate());
        assertEquals("987654321", result.getPhoneNumber());
        assertEquals("ivan.testic@example.com", result.getEmail());
        assertEquals("ivantestic", result.getUserName());

    }


}
