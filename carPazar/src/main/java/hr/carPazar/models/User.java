package hr.carPazar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


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

}
