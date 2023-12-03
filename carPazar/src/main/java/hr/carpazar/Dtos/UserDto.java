package hr.carpazar.Dtos;

import lombok.Data;


@Data
public class UserDto {
    private String firstName;
    private String surname;
    private String birthDate;
    private Integer phoneNumber;
    private String email;
    private String username;
    private String password;
}
