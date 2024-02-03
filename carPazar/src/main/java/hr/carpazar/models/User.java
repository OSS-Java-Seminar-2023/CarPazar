package hr.carpazar.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @Builder.Default
    @Column(name = "id")
    private String id = UUID.randomUUID().toString();

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Builder.Default
    @Column(name = "is_premium", nullable = false)
    private Boolean isPremium = false;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Builder.Default
    @Column(name = "is_admin")
    private Boolean isAdmin = false;

    @Column(name = "birth_date")
    private Date birthDate;
}
