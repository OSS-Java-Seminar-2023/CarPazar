package hr.carpazar.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import java.sql.Date;


@Setter
@Getter
@Entity
public class User {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "user_rating")
    private Integer userRating;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "birth_date")
    private Date birthDate;
}
