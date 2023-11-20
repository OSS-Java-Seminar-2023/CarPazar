package hr.carPazar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


@Setter
@Getter
@Entity
public class Review {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "review", nullable = false, columnDefinition = "text")
    private String review;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating;



}
